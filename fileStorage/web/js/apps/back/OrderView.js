var ItemOrderModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'item-order',
	defaults: {
		"id": null,
		"item": null,
		"note": "",
		"status": 0,
		"account": null
	}
});

var ItemOrderCollection = Backbone.Collection.extend({
	url: window.app.api + 'item-order',
    model: ItemOrderModel
});

var OrderView = Backbone.View.extend({
	
	el: "#mainview",

	collection: null,
	
	cat: null,
	
	websocket: null,
	
	initialize: function(){
	
		// otevři websocket
		this.openWebsocket();	
		
		// items
		this.collection = new ItemOrderCollection;
		this.collection.on("sync", this.render, this);
		this.collection.fetch();		

	},
	
	render: function(){
	
		//
		this.$el.empty();
		
		//
		this.renderItems();
		
	},
	
	renderItems: function(){
		//return;
		
		var thisObject = this;
		var idx = 0;
		this.collection.each(function(item){
			var status = item.get("status"); 
			if(status >= 4){return;}

			var div = $("<div />", {class: "showRoomItem itemItem"});
			
			var table = $("<div />", {class: "showRoomItemTable itemItem"});
			table.append("Stůl: "+item.get("account"));
			div.append(table);
			
			var name = $("<div />", {class: "showRoomItemName itemItem"});
			name.append(item.get("item").name);
			div.append(name);
			
			if(item.get("item").image != null){
				var img = $("<div />", {class: "showRoomItemImage itemItem"});
				img.css("background-image", "url('"+window.app.files + item.get("item").image.original.filePath+"')");
				div.append(img);
			}	
			
			/*var txt = $("<div />", {class: "showRoomItemText itemItem"});
			txt.append(item.get("shortText"));
			div.append(txt);
			
			var price = $("<div />", {class: "showRoomItemPrice itemItem"});
			price.append(item.get("price") + " Kč");
			div.append(price);*/
			
			// statusy
			//var status = item.get("status"); 
			var text = "";
			if(status == 0){
				div.addClass("statusRed");
				text = "Čeká, připravit";
			}
			if(status == 1){
				div.addClass("statusOrange");
				text = "Přípr., je hotovo";
			}
			if(status == 2){
				div.addClass("statusBlue");
				text = "Je hotovo, odnést";
			}
			if(status == 3){
				div.addClass("statusGray");
				text = "Vše OK, vymazat";
			}
			
			// tlačítko
			var order = $('<a>', { text: text, title: text, href: "#", class: "showRoomItemOrder" }).click(function(){
				thisObject.updateOrder(item);	
				//thisObject.animateOrder($(this));
			});
			div.append(order);
			
			//
			thisObject.$el.append(div);
			
			/*div.click(function(){
				alert("Clicked: "+item.get("name"));
				//thisObject.browseCat(item);
			});*/
			
			//animace
			//div.hide().delay(500 + 100*idx).fadeIn(900);			
			//idx++;
		});
	},	

	
	updateOrder: function(order){
	
		var status = order.get("status");
		console.log("Updating order, status: "+status);
		
		var newStatus = status + 1;
		
		//save
		var thisObject = this;
		order.set("status", newStatus);
		order.save({}, {success:function(){
			thisObject.websocket.send("update-order");
		}});
	},
	
	onNewOrder: function(){
		this.collection.fetch();
	},
	
	onUpdateOrder: function(){
		this.collection.fetch();
	},
	
	openWebsocket: function(){
	
		var thisObject = this;

		//máme již otevřeno?
		if(this.websocket != null && this.websocket.readyState !== WebSocket.CLOSED){
			console.log("Websocket already open :(");
		}
		
		// otevřeme
		var url = "ws://" + window.app.protocolLessRoot + "/hello";
		console.log("Opening websocket on: " + url);
		this.websocket = new WebSocket(url);
		 
		this.websocket.onopen = function(evt){
			// For reasons I can't determine, onopen gets called twice
			// and the first time event.data is undefined.
			// Leave a comment if you know the answer.
			if(evt.data === undefined)
				return;

			console.log("Websocket open: ");
			console.log(evt.data);
		};

		this.websocket.onmessage = function(evt){
			console.log("Websocket msg received: ");
			console.log(evt.data);
			
			if(evt.data == "new-order"){
				thisObject.onNewOrder();
			}
			if(evt.data == "update-order"){
				thisObject.onUpdateOrder();
			}
		};

		this.websocket.onclose = function(event){
			writeResponse("Connection closed");
		};
            

	}
	
	
	
});