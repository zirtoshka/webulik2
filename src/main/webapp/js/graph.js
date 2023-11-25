import {showToast} from './utils.js';
import {check} from './form.js';

const canvas = document.getElementById('coordinateCanvas');
const centerX = canvas.width / 2;
const centerY = canvas.height / 2;
const pxR = 50;
const ctx = canvas.getContext('2d');
//export var points = [];


document.addEventListener('DOMContentLoaded', function () {
    drawGraph();
    let storedPoints = sessionStorage.getItem('points');
    if (storedPoints) {
        //points = JSON.parse(storedPoints);
        drawPoints();
    } else {
        sessionStorage.setItem('points', JSON.stringify([]));
    }
    const cleanTable = document.getElementById("clean-table");
    cleanTable.addEventListener('click', doRequestToClear);
});

canvas.addEventListener('click', function (event) {
    if (check.rValue == null) showToast("Can't find coordinates, please choose value for r")
    else {
        let loc = windowToCanvas(canvas, event.clientX, event.clientY);
        let x = xFromCanvas(loc.x);
        let y = yFromCanvas(loc.y);
        let storagePoints = JSON.parse(sessionStorage.getItem('points'));

        storagePoints.push(JSON.stringify({"x": x, "y": y}));

        sessionStorage.setItem('points', JSON.stringify(storagePoints));

        drawPoint(x, y);
        sendToServer(x, y, check.rValue).then(r => console.log(r));
    }
});

export function drawPoints() {
    let points = JSON.parse(sessionStorage.getItem('points'));
    for (const point of points) {
        drawPoint(point.x, point.y);
    }
}


function clearTable() {
    sessionStorage.setItem('points', JSON.stringify([]));

    ctx.clearRect(0, 0, canvas.width, canvas.height);

    drawGraph();

    let table = document.getElementById("results-table");
    let rowCount = table.rows.length;
    for (let i = rowCount - 1; i > 0; i--) {
        table.deleteRow(i);
    }

}

 function doRequestToClear() {
    let action = "clear";
     fetch("app", {
        method: "GET",
        data: action
    }).then(response => {
        clearTable()
    })
        .catch(error => {
            console.error('Error:', error);
        });
}

async function sendToServer(x, y, r) {
    let isForm = false;
    if (x !== null && y !== null && r !== null) {
        try {
            const response = await fetch("app", {
                method: "POST",
                mode: "no-cors",
                redirect: "follow",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify({x, y, r, isForm})
            });
            const json = await response.json();
            if (response.status === 200) {
                if (!document.getElementById("disable-video").checked) {
                    let isKill = false;
                    if (json.result === "kill") {
                        isKill = true;
                    }
                    await check.animations.shoot(x, y, r, isKill);
                }

                let data = [x, y, r, json.nowTime, json.script_time, json.result];

                check.addTableResults(data);

            } else {
                showToast("Server error: " + json.message);
            }
        } catch (error) {
            showToast("Server unreachable :(\nTry again later ");
        }
    }

}

function xFromCanvas(x) {
    return (x - centerX) / pxR;
}

function yFromCanvas(y) {
    return (centerY - y) / pxR;
}

function xToCanvas(x) {
    return (x * pxR) + centerX;
}

function yToCanvas(y) {
    return centerY - (y * pxR);
}

function windowToCanvas(canvas, x, y) {
    let bbox = canvas.getBoundingClientRect();
    return {
        x: x - bbox.left * (canvas.width / bbox.width),
        y: y - bbox.top * (canvas.height / bbox.height)
    };
}


export function drawGraph() {
    const axisLength = 500;
    const r = pxR * check.rValue;

    ctx.clearRect(0, 0, canvas.width, canvas.height);
    shadeRegion(ctx, centerX, centerY, r);

    // Draw X-axis
    drawArrow(ctx, -centerX, centerY, canvas.width, centerY);
    drawTicks(ctx, centerX, centerY, axisLength, 'x');
    drawAxisLabel(ctx, 'X', canvas.width - 10, centerY + 20);

    // Draw Y-axis
    drawArrow(ctx, centerX, centerY * 2, centerX, 0);
    drawTicks(ctx, centerX, centerY, axisLength, 'y');
    drawAxisLabel(ctx, 'Y', centerX - 20, 10);


    drawAxisLabel(ctx, 'R', centerX + r, centerY - 5);
    drawAxisLabel(ctx, 'R/2', centerX + r / 2, centerY - 5);
    drawAxisLabel(ctx, 'R', centerX + 5, centerY - r);
    drawAxisLabel(ctx, 'R/2', centerX + 5, centerY - r / 2);
    drawAxisLabel(ctx, '- R', centerX - r, centerY - 5);
    drawAxisLabel(ctx, '- R/2', centerX - r / 2, centerY - 5);
    drawAxisLabel(ctx, '- R', centerX + 5, centerY + r);
    drawAxisLabel(ctx, '- R/2', centerX + 5, centerY + r / 2);

}

export function drawPoint(x, y) {

    x = xToCanvas(x);
    y = yToCanvas(y);
    ctx.beginPath();
    ctx.fillStyle = 'rgb(17,3,3)';
    ctx.moveTo(x, y);
    // ctx.fillRect(x, y, 3, 3);
    ctx.arc(x, y, 1.5, 0, Math.PI * 2);
    ctx.fill();
    ctx.stroke();
    ctx.fillStyle = 'rgba(245,87,245,0.73)';

}

export function drawArrow(context, fromX, fromY, toX, toY) {
    context.beginPath();
    context.moveTo(fromX, fromY);
    context.lineTo(toX, toY);
    context.stroke();

    // Draw arrowhead
    const headLength = 7;
    const angle = Math.atan2(toY - fromY, toX - fromX);
    context.lineTo(toX - headLength * Math.cos(angle - Math.PI / 6), toY - headLength * Math.sin(angle - Math.PI / 6));
    context.moveTo(toX, toY);
    context.lineTo(toX - headLength * Math.cos(angle + Math.PI / 6), toY - headLength * Math.sin(angle + Math.PI / 6));
    context.stroke();
}

function drawTicks(context, centerX, centerY, length, axis) {
    const numTicks = 11;
    const tickSpacing = length / (numTicks - 1);
    const tickSize = 5;
    context.beginPath();

    for (let i = 0; i < numTicks; i++) {
        const tickPosition = i * tickSpacing - length / 2;
        if (axis === 'x') {
            context.moveTo(centerX + tickPosition, centerY - tickSize / 2);
            context.lineTo(centerX + tickPosition, centerY + tickSize / 2);
        } else {
            context.moveTo(centerX - tickSize / 2, centerY + tickPosition);
            context.lineTo(centerX + tickSize / 2, centerY + tickPosition);
        }
    }

    context.stroke();
}

function drawAxisLabel(context, label, x, y) {
    context.fillStyle = 'rgba(0, 0, 0, 1)'; // Цвет и прозрачность заливки

    context.font = '14px Arial';
    context.fillText(label, x, y);
}

function shadeRegion(context, centerX, centerY, r) {
    context.fillStyle = 'rgba(255, 0, 255, 1)'; // Цвет и прозрачность заливки

    //part of circle
    context.beginPath();
    context.arc(centerX, centerY, r / 2, 0, Math.PI / 2, false);
    context.lineTo(centerX, centerY);
    context.closePath();
    context.fill();

    //squart
    context.beginPath();
    context.rect(centerX - r, centerY, r, -r / 2);
    context.fill();

    //tringale
    context.beginPath();
    context.moveTo(centerX, centerY + r / 2);
    context.lineTo(centerX - r / 2, centerY);
    context.lineTo(centerX, centerY - r / 2);
    context.lineTo(centerX, centerY);
    context.fill();

}