<%@ page import="validator.PointsStorage" %>
<%@ page import="validator.Point" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="assets/index.css">
    <link rel="stylesheet" href="assets/ball.css">

    <title>puk labuk2</title>

</head>

<body>
<div>
    <video style="display: none;" id="krosh-video" src="assets/krosh.mp4" controls="true"
           crossorigin="anonymous"></video>
    <video style="display: none;" id="pin-video" src="assets/pin.mp4" controls="true"
           crossorigin="anonymous"></video>
    <canvas id="tmp" style="display: none;" width="640"></canvas>
    <canvas id="kill" class="overlay" width="640"></canvas>


</div>
<header class="header">
    <%--    <h1> Зайцева Ирина Сергеевна P3209 1993</h1>--%>
    <h1> Zaitseva Irina Sergeevna P3209 90838</h1>
</header>
<br>
<div class="container">
    <div class=input>
        <form id="user-request">
            <h1>Data input</h1>
            <div class="x-input">
                <label id="x-label">X:</label>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="-5" name="checkbox" value="-5"/>
                    <%--                    checked--%>
                    <label for="-5">-5</label>
                </div>

                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="-4" name="checkbox" value="-4"/>
                    <label for="-4">-4</label>
                </div>

                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="-3" name="checkbox" value="-3"/>
                    <label for="-3">-3</label>
                </div>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="-2" name="checkbox" value="-2"/>
                    <label for="-2">-2</label>
                </div>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="-1" name="checkbox" value="-1"/>
                    <label for="-1">-1</label>
                </div>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="0" name="checkbox" value="0"/>
                    <label for="0">0</label>
                </div>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="1" name="checkbox" value="1"/>
                    <label for="1">1</label>
                </div>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="2" name="checkbox" value="2"/>
                    <label for="2">2</label>
                </div>
                <div class="x-checkbox">
                    <input class="x-value" type="checkbox" id="3" name="checkbox" value="3"/>
                    <label for="3">3</label>
                </div>


            </div>
            <br>
            <div class="y-input">
                <label id="y-label">Y: </label>
                <input class="y-text" id="y-value" name="y" type="text" required title="Y must be in range (-5, 3)">
            </div>
            <br>
            <div class="r-input">
                <label id="r-label">R:</label>
                <div class="r-radio">
                    <div class="r-button">
                        <input name="radio" class="r-value" id="r1" type="radio" value="1">
                        <label for="r1">1</label>
                    </div>
                    <div class="r-button">
                        <input name="radio" class="r-value" id="r2" type="radio" value="2">
                        <label for="r2">2</label>
                    </div>
                    <div class="r-button">
                        <input name="radio" class="r-value" id="r3" type="radio" value="3">
                        <label for="r3">3</label>
                    </div>
                    <div class="r-button">
                        <input name="radio" class="r-value" id="r4" type="radio" value="4">
                        <label for="r4">4</label>
                    </div>
                    <div class="r-button">
                        <input name="radio" class="r-value" id="r5" type="radio" value="5">
                        <label for="r5">5</label>
                    </div>
                </div>


            </div>

            <br>

            <div class="button">
                <input class="check-button" type="submit" id="submit-button" value="want to check">
            </div>

        </form>
        <div class="disable-button">
            <label class="item" for="disable-video">disable video</label>
            <input class="item" id="disable-video" type="checkbox">
        </div>
        <div class="clean-button">
            <input class="check-button" id="clean-table" type="button" value="clean points">

        </div>

        <form class="ball-form">
            <!-- ответы -->
            <input type="radio" name="answer" id="Yes" value="Yes"/><span>Yes</span>
            <input type="radio" name="answer" id="It seems like govnocode" value="It seems like<br> govnocode"/><span>It seems like<br> govnocode</span>
            <input type="radio" name="answer" id="Not today" value="Not today"/><span>Не в этот<br>раз</span>
            <input type="radio" name="answer" id="be like Naruto" value="be like<br>Naruto"/><span>be like<br>Naruto</span>
            <input type="radio" name="answer" id="London" value="London"/><span>London</span>
            <input type="radio" name="answer" id="Rubezhka" value="Rubezhka"/><span>Rubezhka</span>
            <input type="radio" name="answer" id="Krosh agrees" value="Krosh agrees"/><span>Krosh agrees</span>
            <input type="radio" name="answer" id="Keep going" value="Keep going"/><span>Keep going</span>
            <input type="radio" name="answer" id="DOM is home" value="DOM is home"/><span>DOM is home</span>
            <input type="radio" name="answer" id="Good job! redo" value="Good job!<br> redo"/><span>Good job!<br> redo</span>
            <input type="radio" name="answer" id="0,01 from burevestnik" value="0,01 from burevestnik"/><span>0,01 from <br> burevestnik</span>
            <input type="radio" name="answer" id="Guys, it's webchik" value="Guys, it's webchik"/><span>Guys, it's<br>webchik</span>
            <input type="radio" name="answer" id="Angular" value="Angular"/><span>Angular</span>
            <input type="radio" name="answer" id="React" value="React"/><span>React</span>
            <input type="radio" name="answer" id="Vue" value="Vue"/><span>Vue</span>
            <!-- шар -->
            <div class="eight__backdrop"></div>
            <!-- раздел с восьмёркой -->
            <div class="eight">
                <!-- лицевая сторона -->
                <div class="eight__fascia">
                    <!-- рисуем восьмёрку -->
                    <div class="eight__number">8</div>
                </div>
                <!-- обратная сторона -->
                <div class="eight__fascia">
                    <!-- окно с ответом -->
                    <div class="eight__window"></div>
                </div>
                <!-- таблица c названиями ответов -->
                <ul>
                    <li>
                        <label for="Yes"></label>
                    </li>
                    <li>
                        <label for="It seems like govnocode"></label>
                    </li>
                    <li>
                        <label for="Not today"></label>
                    </li>
                    <li>
                        <label for="be like Naruto"></label>
                    </li>
                    <li>
                        <label for="London"></label>
                    </li>
                    <li>
                        <label for="Rubezhka"></label>
                    </li>
                    <li>
                        <label for="Krosh agrees"></label>
                    </li>
                    <li>
                        <label for="Keep going"></label>
                    </li>
                    <li>
                        <label for="DOM is home"></label>
                    </li>
                    <li>
                        <label for="Good job! redo"></label>
                    </li>
                    <li>
                        <label for="0,01 from burevestnik"></label>
                    </li>
                    <li>
                        <label for="Guys, it's webchik"></label>
                    </li>
                    <li>
                        <label for="Angular"></label>
                    </li>
                    <li>
                        <label for="Vue"></label>
                    </li>
                    <li>
                        <label for="React"></label>
                    </li>
                </ul>
            </div>
            <!-- кнопка сброса -->
            <input type="reset"/>
        </form>
    </div>


    <div class="graph">
        <h1>Graph</h1>

        <div class="graph-canvas">
            <canvas width="517" height="520" id="coordinateCanvas">
            </canvas>
        </div>
        <br>

    </div>


    <div class="result">
        <table id="results-table">
            <thead>
            <tr>
                <th width="5%">X</th>
                <th width="5%">Y</th>
                <th width="5%">R</th>
                <th width="40%">Current time</th>
                <th width="25%">Script time</th>
                <th width="20%">Result</th>
            </tr>
            </thead>
            <% PointsStorage pointsStorage = (PointsStorage) request.getSession().getAttribute("tableContent");
                if (pointsStorage == null) {%>
            <tbody id="results-content">
            </tbody>
            <% } else { %>

            <tbody id="results-content">
            <% List<Point> list = pointsStorage.getPoints(); %>
            <% for (int i = list.size() - 1; i >= 0; i--) { %>
            <tr>
                <td><%=list.get(i).getX()%>
                </td>
                <td><%=list.get(i).getY()%>
                </td>
                <td><%=list.get(i).getR()%>
                </td>
                <td><%= list.get(i).getTime() %>
                </td>
                <td><%= list.get(i).getScriptTime() %>
                </td>
                </td>
                <td><%= list.get(i).getIsKill() ? "kill"
                        : "miss"%>
                </td>

            </tr>
            <% } %>
            </tbody>
            <% } %>

        </table>
    </div>

</div>

<div id="custom-toast" class="toast">
    <div class="toast-content">

    </div>
</div>



<script src="js/animations.js" type="text/javascript"></script>
<script src="js/form.js" type="module"></script>
<script src="js/utils.js" type="module"></script>
<script src="js/graph.js" type="module"></script>


</body>

</html>