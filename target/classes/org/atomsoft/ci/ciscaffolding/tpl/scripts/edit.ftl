$(function(){
	$("#saveBtn").bind("click",saveData);
	$('.datepicker').datepicker();
});
function saveData(){
    var sform = document.getElementById("${entityName}form");
    sform.action = "/${entityName}/saveupdate";
    sform.enctype="multipart/form-data";
    sform.target="dataFrame";
    sform.submit();
}