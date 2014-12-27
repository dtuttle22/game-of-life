var SIZE  = 30;
var ALIVE = 1;
var DEAD  = 0;

$(function(){
	drawBoard();
	$("#nextGeneration").click(function(){
		sendBoard();
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
