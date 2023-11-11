function validate(x, y, r) {
    //console.log("validX: " + valid_x() + "\nvalidY: " + valid_y() + "\nvalidR: " + valid_R());
    return (validate_x(x) && validate_y(y) && validate_r(r));
}

function validate_x(x) {
    const xValues = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
    let parsedX = parseInt(x);
    if (isNaN(x.trim()) || isNaN(parsedX) || !xValues.includes(parsedX)) {
        showToast("Please choose correct button" + x);
        return false;
    }
    return true;
}

function validate_y(y) {
    const yMin = -5, yMax = 3;
    let parsedY = parseFloat(y);
    if (isNaN(y.trim()) || isNaN(parsedY) || yMin > parsedY || parsedY > yMax) {
        showToast("Please input correct Y value: [-5; 5]");
        return false;
    }
    return true;
}
function validate_r(r){
    const rValues = [1, 2, 3, 4, 5];
    let parsedR = parseFloat(r);
    if (isNaN(r.trim()) || isNaN(parsedR) || !rValues.includes(parsedR)) {
        showToast("Choose only one checkbox" + parsedR);
        return false;
    }return true;}