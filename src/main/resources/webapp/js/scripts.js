var i=1;
function addDemonstrator() {
if(i<10){
	//create json input list element
	var demoNameElement = document.createElement("input");
	var att1 = document.createAttribute("type");
	att1.value = "text";
	var att2 = document.createAttribute("name");
	att2.value = "demo_name"+i;			
	var att3 = document.createAttribute("list");
	att3.value = "json-datalist";
	demoNameElement.setAttributeNode(att1);
	demoNameElement.setAttributeNode(att2);
	demoNameElement.setAttributeNode(att3);
	
	//create no_hours element
	var demoHoursElement = document.createElement("input");
	var att1 = document.createAttribute("type");
	att1.value = "number";
	var att2 = document.createAttribute("name");
	att2.value = "no_hours"+i;			
	var att3 = document.createAttribute("value");
	att3.value = "1";
	demoHoursElement.setAttributeNode(att1);
	demoHoursElement.setAttributeNode(att2);
	demoHoursElement.setAttributeNode(att3);
	
	//create delete button element 
	var demoHoursDelete = document.createElement("input");
	demoHoursDelete.type = "button";
	demoHoursDelete.value = "x";
	var att1 = document.createAttribute("onclick");
	att1.value = "deleteRow(" +i+ ")";
	demoHoursDelete.setAttributeNode(att1);
	var att2 = document.createAttribute("class");
	att2.value = "delete";
	demoHoursDelete.setAttributeNode(att2);
	
	//create table structure
	var trElement = document.createElement("tr");
	trElement.id = i;
	var td1Element = document.createElement("td");
	var td2Element = document.createElement("td");
	var td3Element = document.createElement("td");	
	td1Element.appendChild(demoNameElement);
	td2Element.appendChild(demoHoursElement);
	td3Element.appendChild(demoHoursDelete);	
	trElement.appendChild(td1Element);
	trElement.appendChild(td2Element);
	trElement.appendChild(td3Element);
	document.getElementById("demoNameTbody").appendChild(trElement);
	i=i+1;	
}
//	var button = document.getElementById("add");
//	document.getElementById("demonstrator_names").insertBefore(demoNameElement,button);

}

function deleteRow(num){
	var parent = document.getElementById("demoNameTbody");
	console.log(parent);
	var child = document.getElementById(num);
	console.log(child);
	parent.removeChild(child);
}

function deleteRowZero(){
}

function getMonth(date) {
    var month = date.getMonth() +1;
    return month < 10 ? '0' + month : month; // ('' + month) for string result
}

window.onload=function insertDate() {
	var d = new Date();
	var string = "";
	document.getElementById("input_date").value = d.getFullYear() + "-" + getMonth(d) + "-" + d.getDate();
}

//AJAX content
//var dataList = document.getElementById('json-datalist');
//var input = document.getElementById('demo_name0');

// Create a new XMLHttpRequest.
var request = new XMLHttpRequest();

// Handle state changes for the request.
request.onreadystatechange = function(response) {
  if (request.readyState === 4) {
    if (request.status === 200) {
		// Parse the html information 
		var names = request.responseText.split("\n");
		for(var k=0;k<names.length;k++)
		{
			var demoNameElement = document.createElement("option");
			var att1 = document.createAttribute("value");
			att1.value = names[k];
			demoNameElement.setAttributeNode(att1);
			document.getElementById("json-datalist").appendChild(demoNameElement);
		}
    }
  }
};

// Update the placeholder text.
//input.placeholder = "Loading options...";

// Set up and make the request.
request.open('GET', 'html-elements.html', true);
request.send();
