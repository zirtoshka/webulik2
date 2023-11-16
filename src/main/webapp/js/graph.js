import { showToast} from './utils.js';
import {check} from './form.js';
// document.addEventListener('DOMContentLoaded', drawGraph);
document.addEventListener('DOMContentLoaded', function () {
    drawGraph();
    const storedPoints = sessionStorage.getItem('points');
    if (storedPoints) {
        points = JSON.parse(storedPoints);
        drawPointsFromJson(storedPoints);
    }
    canvas.addEventListener('click', function (event){
        if(check.rValue == null) showToast("Can't find coordinates, please choose value for r")
        else {
            let loc = windowToCanvas(canvas, event.clientX, event.clientY);
            let x = xFromCanvas(loc.x);
            let y = yFromCanvas(loc.y);
            const newPoint = { x: x, y: y };
            points.push(newPoint); // Добавление новой точки в массив

            drawPoint(x, y);

            // Сохранение обновленных точек в sessionStorage
            sessionStorage.setItem('points', JSON.stringify(points));
            sendToServer(x, y, check.rValue).then(r => console.log(r));

        }
    });


});
function drawPointsFromJson(jsonPoints) {
    const parsedPoints = JSON.parse(jsonPoints);
    for (const point of parsedPoints) {
        drawPoint(point.x, point.y);
    }
}



const canvas = document.getElementById('coordinateCanvas');
var x,y;
const centerX = canvas.width / 2;
const centerY = canvas.height / 2;
const pxR=50;
const ctx = canvas.getContext('2d');
let points = []; // Инициализация массива точек



// canvas.addEventListener('click', function (event){
//     if(check.rValue == null) showToast("Can't find coordinates, please choose value for r")
//     else {
//         let loc = windowToCanvas(canvas, event.clientX, event.clientY);
//         let x = xFromCanvas(loc.x);
//         let y = yFromCanvas(loc.y);
//         console.log(x);
//         console.log(y);
//         drawPoint(x,y);
//         sendToServer(x, y, check.rValue);
//         sessionStorage.setItem('points', JSON.stringify(updatedPoints));
//
//     }
// });

async function sendToServer(x,y,r) {
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
            console.log(1);
            const json = await response.json();
            if (response.status === 200) {
                console.log(2);
                if (!document.getElementById("disable-video").checked) {
                    let isKill = false;
                    if (json.result === "kill") {
                        isKill = true;
                    }
                    await check.animations.shoot(x, y, r, isKill);
                }

                console.log(3);
                var data = [x, y, r,  json.nowTime, json.script_time,json.result];
                console.log("lolik");
                console.log(data);
                check.addTableResults(data);

            } else {
                check.showToast("Server error: " + json.message);
            }
        } catch (error) {
            console.log(ErrorEvent + error);
            showToast("Server unreachable :(\nTry again later ");
        }
    }

}
function xFromCanvas(x){
    return (x - centerX)/pxR;
}
function yFromCanvas(y){
    return (centerY - y)/pxR;
}
function xToCanvas(x){
    return (x * pxR) + centerX;
}
function yToCanvas(y){
    return centerY - (y * pxR);
}

function windowToCanvas(canvas, x, y){
    let bbox = canvas.getBoundingClientRect();
    return {x: x -bbox.left * (canvas.width / bbox.width),
        y: y - bbox.top * (canvas.height / bbox.height)
    };}


 export function   drawGraph() {
    // const canvas = document.getElementById('coordinateCanvas');
    // const centerX = canvas.width / 2;
    // const centerY = canvas.height / 2;
    const axisLength = 500;
    const r = pxR*check.rValue; // Установите ваше значение r здесь

    // Clear the canvas
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    // Shade the specified region
    shadeRegion(ctx, centerX, centerY, r);

    // Draw X-axis
    drawArrow(ctx, -centerX, centerY, canvas.width, centerY);
    drawTicks(ctx, centerX, centerY, axisLength, 'x');
    drawAxisLabel(ctx, 'X', canvas.width - 10, centerY + 20);

    // Draw Y-axis
    drawArrow(ctx, centerX, centerY*2, centerX, 0);
    drawTicks(ctx, centerX, centerY, axisLength, 'y');
    drawAxisLabel(ctx, 'Y', centerX - 20, 10);


    drawAxisLabel(ctx, 'R', centerX+r, centerY-5);
    drawAxisLabel(ctx, 'R/2', centerX+r/2, centerY-5);
    drawAxisLabel(ctx, 'R', centerX+5, centerY-r);
    drawAxisLabel(ctx, 'R/2', centerX+5, centerY-r/2);
    drawAxisLabel(ctx, '- R', centerX-r, centerY-5);
    drawAxisLabel(ctx, '- R/2', centerX-r/2, centerY-5);
    drawAxisLabel(ctx, '- R', centerX+5, centerY+r);
    drawAxisLabel(ctx, '- R/2', centerX+5, centerY+r/2);

}

function drawPoint(x, y){
    x = xToCanvas(x);
    y = yToCanvas(y);
    ctx.fillStyle = 'rgb(17,3,3)';
    ctx.fillRect(x, y, 3, 3);
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
    context.arc(centerX, centerY, r / 2, 0, Math.PI/2, false);
    context.lineTo(centerX, centerY);
    context.closePath();
    context.fill();

    //squart
    context.beginPath();
    context.rect(centerX - r, centerY, r, -r / 2);
    context.fill();

    //tringale
    context.beginPath();
    context.moveTo(centerX, centerY+r/2);
    context.lineTo(centerX - r/2 , centerY);
    context.lineTo(centerX, centerY - r / 2);
    context.lineTo(centerX, centerY);
    context.fill();

}