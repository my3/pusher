function onCreate(id)
{
if(id ==1){
console.log("ContextMenu iphone created \n");
}
if(id == 2){
console.log("ContextMenu ipad created   \n");
}
if(id ==3){
console.log("ContextMenu Add number to you contacts created \n");
}
}

function getuserinfo( userinfo  )
{



}


chrome.extension.onMessage.addListener(
  function(request, sender, sendResponse) {
	console.log("message handler executing ");
    if(request.greeting == "create"){
	var id = chrome.contextMenus.create({"id" : "1" ,"title": "Call %s from your iphone  ", "contexts":["selection"]}, onCreate(1));
    var id = chrome.contextMenus.create({"id" : "2" , "title" : "Call %s from your ipad  ","contexts" : ["selection"]},onCreate(2));
	var id = chrome.contextMenus.create({"id" : "3" , "title" : "Add %s to your contacts ","contexts" : ["selection"]},onCreate(3));
	}
	else
	{
	chrome.contextMenus.remove("1");
	chrome.contextMenus.remove("2");
	chrome.contextMenus.remove("3");
	}
  });
  
 $(document).ready(function(){
	console.log("jquery working iun background \n");
 }); 