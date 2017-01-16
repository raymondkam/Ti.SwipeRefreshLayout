var win = Ti.UI.createWindow({
   backgroundColor:'#f7fffa'
});

var listTemplates = {
   'messageTemplateUser' : {
      properties:{
         backgroundColor:'#e9e9f2',
         height:Ti.UI.SIZE
      },
      childTemplates: [{
         type: 'Ti.UI.Label',
         bindId: 'from',
         properties: {
            width : '90%',
            color : '#90000000',
            font : {fontSize:10, fontWeight:'bold'},
            left : '12dp',
            top : '6dp',
            textAlign : Ti.UI.TEXT_ALIGNMENT_RIGHT
         }
      },
      {
         type: 'Ti.UI.View',
         bindId: 'labelView',
         properties: {
            top : '20dp',
            layout:'vertical',
            height:Ti.UI.SIZE
         },
         childTemplates: [{
            type: 'Ti.UI.Label',
            bindId: 'message',
            properties: {
               width : '90%',
               color : '#696969',
               font : {fontSize:14},
               left : '12dp',
               textAlign : Ti.UI.TEXT_ALIGNMENT_RIGHT
            }
         }]
      }]
   }
};

var listView = Ti.UI.createListView({
   top: 20,
   height: Ti.UI.FILL,
   templates: listTemplates,
   defaultItemTemplate: 'messageTemplateUser',
   backgroundColor : 'transparent',
   separatorColor : '#dcdce4'
});

var swipeRefreshModule = require('com.rkam.swiperefreshlayout');
var swipeRefresh = swipeRefreshModule.createSwipeRefresh({
    view: listView
});

var fakeData = [
   {from: "User 1", message:"Hello there"},
   {from: "New User", message:"Hi how are you doing?"},
   {from: "User 1", message:"Im fine and you?"},
   {from: "New User", message:"Im pretty well too"},
   {from: "User 1", message:"Good to hear it"},
   {from: "New User", message:"And what about your Android phone?"},
   {from: "User 1", message:"oh, it is also doing well"},
   {from: "New User", message:"Ok, see you soon then"},
   {from: "User 1", message:"Ok, bye bye!"}
];

var sections = [];
var messagesSection = Ti.UI.createListSection();
var currentUser = "User 1";
sections.push(messagesSection);
listView.setSections(sections);

win.add(swipeRefresh);
swipeRefresh.addEventListener('refreshing', onRefreshstart);
function onRefreshstart(){
   console.log('refreshing list...');
   setTimeout(function () {
      var history = [];
         var _m = {
            from: {text:'Refresh Control'},
            message:{text:"I'm fresh!!"},
            template:'messageTemplateUser'
         };
         history.push(_m);
      listView.getSections()[0].appendItems(history);
      listView.scrollToItem(0, listView.getSections()[0].items.length - 1);
      swipeRefresh.setRefreshing(false);
   }, 2000);
}

setTimeout(function () {
   var history = [];
   for(var i = 0; i < fakeData.length; i++){
      var _m = {
         from: {text:fakeData[i].from},
         message:{text:fakeData[i].message},
         template:'messageTemplateUser'
      };

      if(fakeData[i].from === currentUser){
         _m.template = "messageTemplateUser";
      }
      history.push(_m);
   }
   listView.getSections()[0].appendItems(history);
   listView.scrollToItem(0, listView.getSections()[0].items.length - 1);
}, 1000);
win.open();
