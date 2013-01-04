var cm = require("context-menu");
var googleItem = cm.Item({
  label: "MyiPhone5",
  data: "https://pusher.edeadlock.com/linker?uid=1000&did=10001"
});
var wikipediaItem = cm.Item({
  label: "MyGal2",
  data: "https://pusher.edeadlock.com/linker?uid=1000&did=10001"
});
var searchMenu = cm.Menu({
  label: "Open Link in",
  context: cm.SelectorContext("a[href]"),
  contentScript: 'self.on("click", function (node, data) {' +
                 '  var searchURL = data + node.textContent;' +
                 '  window.location.href = searchURL;' +
                 '});',
  items: [googleItem, wikipediaItem]
});
