var i=1;
function addDemonstrator() {
	//create json input list element
	var demoNameElement = document.createElement("input");
	var att1 = document.createAttribute("type");
	att1.value = "text";
	var att2 = document.createAttribute("id");
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
	var att2 = document.createAttribute("id");
	att2.value = "no_hours"+i;			
	var att3 = document.createAttribute("value");
	att3.value = "1";
	demoHoursElement.setAttributeNode(att1);
	demoHoursElement.setAttributeNode(att2);
	demoHoursElement.setAttributeNode(att3);
	
	//create table structure
	var trElement = document.createElement("tr");
	var td1Element = document.createElement("td");
	var td2Element = document.createElement("td");
	td1Element.appendChild(demoNameElement);
	td2Element.appendChild(demoHoursElement);
	trElement.appendChild(td1Element);
	trElement.appendChild(td2Element);
	document.getElementById("demoNameTable").appendChild(trElement);
	i=i+1;	
//	var button = document.getElementById("add");
//	document.getElementById("demonstrator_names").insertBefore(demoNameElement,button);

}

//AJAX content
var dataList = document.getElementById('json-datalist');
var input = document.getElementById('demo_name0');

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
