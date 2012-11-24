$(funtion(){
	$("#saveBtn").bind("click",saveData);
});
function saveData(){
    var sform = document.getElementById("${entityName}form");
    sform.action = link;
    sform.target="dataFrame";
    sform.submit();
}