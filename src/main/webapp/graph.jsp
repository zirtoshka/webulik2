<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Canvas with Shaded Area</title>
  <style>
    canvas {
      border: 1px solid #000;
    }
  </style>
</head>
<body>
<canvas id="coordinateCanvas" width="510" height="510"></canvas>
<script>
  document.addEventListener('DOMContentLoaded', drawGraph);

  function drawGraph() {
    const canvas = document.getElementById('coordinateCanvas');
    const ctx = canvas.getContext('2d');
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    console.log(centerX + 'ffff'+ centerY);
    const axisLength = 500;
    const r = 50*4; // Установите ваше значение r здесь

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

  function drawArrow(context, fromX, fromY, toX, toY) {
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
</script>
</body>
</html>
