$(function(){
	$("#saveBtn").bind("click",saveData);
	$('.datepicker').datepicker();
});
function saveData(){
    var sform = document.getElementById("${entityName}form");
    sform.action = link;
    sform.target="dataFrame";
    sform.submit();
}