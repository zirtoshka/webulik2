"use script";


const resultsDataKey = "results";
import {showToast} from './utils.js';
import {drawGraph} from "./graph.js";

export class Checker {


    constructor() {

        this.yInput = document.getElementById("y-value");
        this.rRadios = document.getElementsByName("radio");
        this.submit = document.getElementById("submit-button")
        this.xCheckboxes = document.getElementsByName("checkbox");
        this.xValues = new Array();


        this.rValue;

        this.disableVideoCheckbox = document.getElementById("disable-video");


        let disableVideoState = localStorage.getItem("disable-video-state");
        this.disableVideoCheckbox.checked = disableVideoState === "true";


        this.setupEventListenersX();
        this.setupEventListenersR();
        this.setupEventListenersY();
        this.setupEventListenersDIsableVideo();

        this.sessionStorage = window.sessionStorage;
        this.resultsTable = document.getElementById("results-content");
        // this.initTableResults();
        this.restoreFormValues();


        var form = document.getElementById("user-request");
        form.addEventListener("submit", this.formSubmitHandler.bind(this));
        this.animations = new AnimationProcessor();


    }


    setupEventListenersDIsableVideo() {
        this.disableVideoCheckbox.addEventListener('click', this.handleDisableVideoSelectChange.bind(this));
    }

    handleDisableVideoSelectChange(event) {
        localStorage.setItem("disable-video-state", this.disableVideoCheckbox.checked);
    }

    setupEventListenersY() {
        this.yInput.addEventListener("input", this.handleYInputChange.bind(this));
    }


    handleYInputChange(event) {
        this.yInput.value = event.target.value;
        localStorage.setItem("y-value", this.yInput.value);
    }

    setupEventListenersR() {
        this.rRadios.forEach(radioButton => {
            radioButton.addEventListener("change", this.handleRadioChange.bind(this))
        });

    }

    setupEventListenersX() {
        this.xCheckboxes.forEach(checkbox => {
            checkbox.addEventListener("change", this.handleCheckboxChange.bind(this))
        });
    }


    handleRadioChange(event) {
        this.rValue = event.target.value;
        localStorage.setItem("r-value", this.rValue);
        drawGraph();
        console.log(this.rValue);
    }

    handleCheckboxChange(event) {
        let checkboxValue = event.target.value;
        // Проверяем, был ли данный чекбокс отмечен или снят
        if (event.target.checked) {
            // Если отмечен, добавляем значение в массив
            this.xValues.push(checkboxValue);
        } else {
            // Если снят, удаляем значение из массива
            const index = this.xValues.indexOf(checkboxValue);
            if (index !== -1) {
                this.xValues.splice(index, 1);
            }
        }
        // Сохраняем обновленные значения в localStorage
        localStorage.setItem("x-value", JSON.stringify(this.xValues));
        console.log(this.xValues);
    }


    validateAndParse(x, y, r) {
        const xValues = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
        const yMin = -5, yMax = 3;
        const rValues = [1, 2, 3, 4, 5];
        let parsedY, parsedR;


        let parsedX = x.map(value => parseInt(value.trim()));

        if (parsedX.length == 0) {
            showToast("Please choose value for x. It can't be null");
            return [null, null, null];
        }
        parsedX.forEach(value => {
            if (isNaN(value) || !xValues.includes(value)) {
                showToast("Please choose correct button " + value);
                return [null, null, null];
            }
        });

        parsedY = parseFloat(y);
        // console.log(/parsedY)
        if (isNaN(y.trim()) || isNaN(parsedY) || yMin >= parsedY || parsedY >= yMax) {
            showToast("Please input correct Y value: (-5; 3)");
            return [null, null, null];
        }

        parsedR = parseInt(r);
        if (isNaN(r.trim()) || isNaN(parsedR) || !rValues.includes(parsedR)) {
            showToast("Choose only one checkbox" + parsedR);
            return [null, null, null];
        }


        return [parsedX, parsedY, parsedR];
    }

    async formSubmitHandler(e) {
        e.preventDefault();
        this.submit.textContent = "Checking...";
        this.submit.disabled = true;
        let isForm = true;

        const [x, y, r] = this.validateAndParse(this.xValues, this.yInput.value, this.rValue);

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
                }).then(response => {
                    if (response.redirected) {
                        window.location.href = response.url;
                    }
                }).catch(function (err) {
                    console.info(err + " url: " + url);
                });
                // const json = await response.json();
                // if (response.status === 200) {
                //     if (!document.getElementById("disable-video").checked) {
                //         let isKill = false;
                //         if (json.result === "kill") {
                //             isKill = true;
                //         }
                //         await this.animations.shoot(x, y, r, isKill);
                //     }
                //
                //
                //     var data = [x, y, r, json.now, json.script_time, json.result];
                //     this.addTableResults(data);
                // } else {
                //     this.showToast("Server error: " + json.message);
                // }
            } catch (error) {
                console.log(ErrorEvent + error);
                showToast("Server unreachable :(\nTry again later ");
            }
        }

        this.submit.disabled = false;
        this.submit.textContent = "Check";

    }

    initTableResults() {
        let data = this.sessionStorage.getItem(resultsDataKey);
        if (data === null) return;
        data.split(";").forEach(rowData => {
            let row = this.resultsTable.insertRow();
            rowData.split(",").forEach(cellData =>
                row.insertCell().innerHTML = cellData
            )
        })
    }

    addTableResults(rowData) {
        let row = this.resultsTable.insertRow(0);
        document.querySelectorAll('td[style="color: blue;"]').forEach(cell => cell.removeAttribute("style"));
        document.querySelectorAll('td[style="color: red;"]').forEach(cell => cell.removeAttribute("style"));

        rowData.forEach(cellData => {
            var cell = row.insertCell();
            cell.innerHTML = cellData;

            if (cellData === "kill") {
                cell.style = "color: blue;";
            } else if (cellData === "miss") {
                cell.style = "color: red;";
            }
        });

        let lastData = this.sessionStorage.getItem(resultsDataKey);
        this.sessionStorage.setItem(resultsDataKey, rowData.toString() + (lastData ? ";" + lastData : ""));
    }


    restoreFormValues() {
        // Восстановление значений полей из localStorage
        const xValuesNew = localStorage.getItem("x-value");
        if (xValuesNew) {
            this.xValues = JSON.parse(xValuesNew);

            this.xCheckboxes.forEach(checkbox => {
                checkbox.checked = this.xValues.includes(checkbox.value);
            });
        }

        const yValue = localStorage.getItem("y-value");
        if (yValue) {
            this.yInput.value = yValue;
        }

        const rValueNew = localStorage.getItem("r-value");
        if (rValueNew) {
            this.rValue = rValueNew;
            this.rRadios.forEach(radioButton => {
                radioButton.checked = radioButton.value == this.rValue;
            });

        }
    }


}

export var check = new Checker();