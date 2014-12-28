var SIZE  = 30;
var ALIVE = 1;
var DEAD  = 0;
var interval;

$(function(){
	drawBoard();
	var nextButton = $("#nextGeneration");
	var startButton = $("#start");
	var clearButton = $("#clear");
	
	startButton.click(function(){
		disable($(this), nextButton, clearButton);
		start();
	});
	
	$("#stop").click(function(){
		enable(startButton, nextButton, clearButton);
		stop();
	});
	
	nextButton.click(function(){
		sendBoard();
	});
	
	clearButton.click(function(){
		$(".cell").removeClass("alive");
	});
	
	$(".cell").click(function(){
		$(this).toggleClass("alive");
	});
	
});

function drawBoard(){
	for (var i = 0; i<SIZE; i++){
		for (var j =0; j<SIZE; j++){
			addCell(i + "-" + j, "board");
		}
	}
}

function addCell(cellId, idDestination){
	$('<div/>', {
	    "id": cellId,
	    "class": 'cell'
	}).appendTo('#' + idDestination);
}

function start(){
	interval = setInterval(sendBoard, 500);
}

function stop(){
	clearInterval(interval);
}

function sendBoard(){
	$.ajax({
		url: 'board',
		type: "POST",
		data: '{"board":' + JSON.stringify(getBoard()) + '}',
		contentType: 'application/json; charset=utf-8',
		dataType: 'json',
		success: function (data){
			updateBoard(data);
		}
	});}

function getBoard(){
	var board = new Array();
	for (var i = 0; i < SIZE; i++){
		var row = $("div[id^='" + i + "-" + "']");
		board.push(getRowValues(row));
	}
	return board;
}

function getRowValues(row){
	return row.map(function(){
		return this.classList.contains("alive") ? ALIVE : DEAD;
	}).get();
}

function updateBoard(board){
	var array = board.board;
	$.each(array, function(rowIndex, row){
		$.each(row, function (colIndex, cellValue){
			var id = rowIndex + "-" + colIndex;
			if (cellValue == ALIVE){
				$("#" + id).addClass("alive");
			} else {
				$("#" + id).removeClass("alive");
			}
		});
	});
}

function disable(){
  for (var i = 0; i < arguments.length; i++) {
	  arguments[i].attr('disabled','disabled');
  }
}

function enable(){
  for (var i = 0; i < arguments.length; i++) {
	  arguments[i].removeAttr('disabled');
  }	
}
