console.log("Get prepared to launch extension ");

function getSelText()
{
var t = "";
  if(window.getSelection){
    t = window.getSelection();
  }else if(document.getSelection){
    t = document.getSelection();
  }else if(document.selection){
    t = document.selection.createRange().text;
  }
  return t;
}

function mouseup(){
  var f = getSelText().toString().replace(/-/g,"");
  console.log("@"+f+"@");
  var regex = /[0-9]{10}/;
  var res = f.search(regex);
  console.log(res);
  if( res >= 0	 ){
  console.log("match found");
  chrome.extension.sendMessage({greeting: "create"}, function(response) {
  console.log(response.farewell);
});
  }
  else
  {
  console.log("match  not found");
  chrome.extension.sendMessage({greeting: "destroy"}, function(response) {
  console.log(response.farewell);
  });
  }
}

$(document).ready(function(){
  $(document).bind("mouseup", mouseup);
});

