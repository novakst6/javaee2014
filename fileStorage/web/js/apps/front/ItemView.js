var ItemModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'item',
	defaults: {
		"id": null,
		"name": "",
		"shortText": "",
		"longText": "",
		"category": null,
		"price": 0,
		"terminalType": null,
		"image": null
	},
	name: "Položka sortimentu"
});

var ItemCollection = Backbone.Collection.extend({
	url: window.app.api + 'item',
    model: ItemModel
});

var CatModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'item-cat',
	defaults: {
		"id": null,
		"name": "",
		"memo": "",
		"parent": null,
		"image": null
	}
});

var CatCollection = Backbone.Collection.extend({
	url: window.app.api + 'item-cat',
    model: CatModel
});

var ItemView = Backbone.View.extend({
	
	el: "#mainview",

	items: null,
	cats: null,
	
	cat: null,
	
	ordering: null,
	
	initialize: function(){
	
		// ordering
		this.ordering = new OrderingView();
	
		// cats
		this.cats = new CatCollection;
		this.cats.on("sync", this.render, this);
		
		// items
		this.items = new ItemCollection;
		this.items.on("sync", this.fetchCats, this);
		this.items.fetch();
		
	},
	
	fetchCats: function(){ this.cats.fetch(); },
	
	render: function(){
	
		//
		this.$el.empty();
		this.$el.append("<h1>OK</h1>");
		
		// tlačítko zpět
		var thisObject = this;
		var btn = $("<div id='backButton'> &lt; zpět</div>");
		btn.hide();
		$("#frameholder").append(btn);
		btn.click(function(){
			thisObject.navBack();
		}).hide();
		
		//this.browseCat(this.cats.findWhere({id:3}));
		this.browseCat(null);
	},
	
	catsRendered: 0,
	renderCats: function(myCats){
		var thisObject = this;
		var idx = 0;
		jQuery.each(myCats, function(index, item){
			/*console.log("Found CAT, ID: "+item.get("id"));
			if(item.get("parent") != null){
				console.log("Parent ID: "+item.get("parent").id);
			}else{
				console.log("Parent ID: null");
			}	*/

			var div = $("<div />", {class: "showRoomItem catItem"});
			
			var name = $("<div />", {class: "showRoomItemName catItem"});
			name.append(item.get("name"));
			div.append(name);
			
			if(item.get("image") != null){
				var img = $("<div />", {class: "showRoomItemImage catItem"});
				img.css("background-image", "url('"+window.app.files + item.get("image").original.filePath+"')");
				div.append(img);
			}	
			
			var txt = $("<div />", {class: "showRoomItemText catItem"});
			txt.append(item.get("memo"));
			div.append(txt);
			
			//
			thisObject.$el.append(div);
			
			div.click(function(){
				//alert("Clicked: "+item.get("name"));
				thisObject.browseCat(item);
			});
			
			//animace
			div.hide().delay(500 + 100*idx).fadeIn(900);			
			idx++;
		});
		this.catsRendered = idx;
	},
	
	renderItems: function(myItems){
		var thisObject = this;
		var idx = this.catsRendered;
		jQuery.each(myItems, function(index, item){

			var div = $("<div />", {class: "showRoomItem itemItem"});
			
			var name = $("<div />", {class: "showRoomItemName itemItem"});
			name.append(item.get("name"));
			div.append(name);
			
			if(item.get("image") != null){
				var img = $("<div />", {class: "showRoomItemImage itemItem"});
				img.css("background-image", "url('"+window.app.files + item.get("image").original.filePath+"')");
				div.append(img);
			}	
			
			var txt = $("<div />", {class: "showRoomItemText itemItem"});
			txt.append(item.get("shortText"));
			div.append(txt);
			
			var price = $("<div />", {class: "showRoomItemPrice itemItem"});
			price.append(item.get("price") + " Kč");
			div.append(price);
			
			var order = $('<a>', { text: "Objednat", title: "Objednat", href: "#", class: "showRoomItemOrder" }).click(function(){
				thisObject.orderItem(item);	
				thisObject.animateOrder($(this));
			});
			div.append(order);
			
			//
			thisObject.$el.append(div);
			
			/*div.click(function(){
				alert("Clicked: "+item.get("name"));
				//thisObject.browseCat(item);
			});*/
			
			//animace
			div.hide().delay(500 + 100*idx).fadeIn(900);			
			idx++;
		});
	},
	
	animateOrder: function(btn){
		console.log("Animating");
		var oldText = btn.text();
		btn
			.fadeOut(500, function(){btn.text("✔ OK");btn.addClass("orderOK");})			
			.fadeIn(500)
			.delay(2000)
			.fadeOut(500, function(){btn.text(oldText);btn.removeClass("orderOK");})
			.fadeIn(500);
	},
	
	orderItem: function(item){
		//alert("Ordering item: "+item.get("name"));
		
		//
		this.ordering.placeOrder(item);
	},
	
	navStack: [],
	
	navBack: function(){
	
		if(this.navStack.length > 1){
			this.navStack.pop();
			var backToCat = this.navStack.pop();
			console.log("Navigating back to: "+backToCat.get("name"));
			this.browseCat(backToCat);
		}else{
			console.log("Navigating back to root");
			this.browseCat(null);
		}
	},
	
	renderNav: function(theCat){
		
		console.log("Rendering nav for cat: " + theCat);
		
		if(theCat == null){
			$("#headview h1").text("Vítejte");
			$("#backButton").fadeOut();
			
		}else{
			this.navStack.push(theCat);
			$("#headview h1").text(theCat.get("name"));
			$("#backButton").fadeIn();
		}
		
		

		
	},
	
	browseCat: function(theCat){
	
		//
		this.renderNav(theCat);
	
		//
		console.log("BrowseCat:");
		console.log(theCat);
		this.$el.empty();
	
		// najít kategorie
		var myCats = this.cats.filter(function(obj){ 
			if(obj.get("parent") == null){
				if(theCat == null){return true;}
				return false;
			}else{
				if(theCat == null){return false;}
				if(theCat.get("id") == obj.get("parent").id){return true;}
				return false;
			}
			return false;
		});

		// 
		/*jQuery.each(myCats, function(index, item){
			console.log("Found CAT, ID: "+item.get("id"));
			if(item.get("parent") != null){
				console.log("Parent ID: "+item.get("parent").id);
			}else{
				console.log("Parent ID: null");
			}			
		});*/
		
		// najít kategorie
		var myItems = this.items.filter(function(obj){ 
			if(obj.get("category") == null){
				if(theCat == null){return true;}
				return false;
			}else{
				if(theCat == null){return false;}
				if(theCat.get("id") == obj.get("category").id){return true;}
				return false;
			}
			return false;
		});
		
		// 
		/*jQuery.each(myItems, function(index, item){
			console.log("Found ITEM, Name: "+item.get("name")+" ID: "+item.get("id"));
			if(item.get("category") != null){
				console.log("Cat ID: "+item.get("category").id);
			}else{
				console.log("Cat ID: null");
			}			
		});*/
		
		// render
		this.renderCats(myCats);
		this.renderItems(myItems);
		
		
	}

	
});

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

var OrderingView = Backbone.View.extend({
	
	accountID: null,
	
	websocket: null, 

	initialize: function(){
	
		// vyřeš id
		if(localStorage.getItem("myAccountID") === null){
			this.accountID = this.generateID();
			localStorage.setItem("myAccountID", this.accountID);
			//alert("AccountID generated: "+this.accountID);
		} else {
			this.accountID = localStorage.getItem("myAccountID");
			//alert("AccountID loaded: "+this.accountID);
		}
		
		// přilep na obrazovku
		$("#frameholder").append("<div id='tableInfo'>Stůl: " + this.accountID + "</div>");
		
		// otevři websocket
		this.openWebsocket();
		
		
	},
	
	placeOrder: function(item){
	
		console.log("Placing order of: " + item.get("name"));
		
		var newOrder = new ItemOrderModel();
		newOrder.set("item", {id: item.get("id")});
		newOrder.set("account", this.accountID);
		
		// save
		var thisObject = this;
		newOrder.save({}, {success:function(){
			thisObject.websocket.send("new-order");
		}});
		
	
	},
	
	openWebsocket: function(){

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
		};

		this.websocket.onclose = function(event){
			writeResponse("Connection closed");
		};
            

	},


	generateID: function(){
		var alphaChars = "abcdef";
		var numChars = "0123456789";
		var id = "";
		for( var i=0; i < 2; i++ ){
			id += alphaChars.charAt(Math.floor(Math.random() * alphaChars.length));
		}
		for( var i=0; i < 2; i++ ){
			id += numChars.charAt(Math.floor(Math.random() * numChars.length));
		}
		return id;
	}
});