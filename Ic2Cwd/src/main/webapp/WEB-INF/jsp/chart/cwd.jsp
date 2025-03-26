 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
    <title>인천2호선 혼잡도 관제시스템</title>
	<meta charset="UTF-8">
	<script src="<%=request.getContextPath()%>/js/jquery.js"></script>
<script>

var AudioContext;
var audioContext;

/* window.onload = function() {
    navigator.mediaDevices.getUserMedia({ audio: true }).then(() => {
        AudioContext = window.AudioContext || window.webkitAudioContext;
        audioContext = new AudioContext();
    }).catch(e => {
        console.error(`Audio permissions denied: ${e}`);
    });
} */
function button_click() {
  var audio = new Audio('cwd_sound.mp3');
  audio.play();
}

// DOM api로 audio element 얻기
function button2_click() {
  var node = document.querySelector('#audio1');
  node.play();
}

window.onload = function() {
	setTimeout(function () {
		$("#btn_cwd").trigger("click");
	}, 100);
	//자식창에서 부모창 값 받음
	var tt1 =opener.opVal1;
	var tt2 =opener.opVal2;
    console.log(tt1);
    $("button").text(tt1+" 열차 현재 "+tt2+"입니다");
}

  </script>
</head>
<body style="overflow: hidden;">
  <!-- <button onclick="button_click()"  style="display:none;">1 Audio</button> -->
  <button class="blink" onclick="button2_click()" id="btn_cwd" style="width:100vw;height:100vh;font-size:60px;font-weight:bold;">경고창</button>
  <audio src="../../../sound/cwd_sound.mp3" id="audio1" ></audio>
</body>
</html>