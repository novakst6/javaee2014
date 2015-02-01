var WebsocketView = Backbone.View.extend({

	name: "Websocket test",
	
	url: null,
	websocket: null, 

	initialize: function(){
		
		this.url = "ws://" + window.app.protocolLessRoot + "/hello";
	
		this.render();
	},
	
	destroy: function(){
		this.remove();
	},
	
	render: function(){
		
		// title
		this.$el.empty();
		this.$el.append("<h1>" + this.name + "</h1>");
		
		//
		this.$el.append(
			$("<button>Poslat zprávu</button>").button({
				icons: {
					primary: "ui-icon-wrench"
				}
			}).click(function(){
				websocket.send("Jak je?");
			})
		);
		
		//
		this.$el.append($("<div />", {id: "websocketHolder"}));
		
		//
		this.openWebsocket();
	},
	
	openWebsocket: function(){

		//máme již otevřeno?
		if(this.websocket != null && this.websocket.readyState !== WebSocket.CLOSED){
			console.log("Websocket already open :(");
		}
		
		// otevřeme
		console.log("Opening websocket on: " + this.url);
		websocket = new WebSocket(this.url);
		 
		websocket.onopen = function(evt){
			// For reasons I can't determine, onopen gets called twice
			// and the first time event.data is undefined.
			// Leave a comment if you know the answer.
			if(evt.data === undefined)
				return;

			console.log("Websocket open: ");
			console.log(evt.data);
		};

		websocket.onmessage = function(evt){
			console.log("Websocket msg received: ");
			console.log(evt.data);
		};

		websocket.onclose = function(event){
			writeResponse("Connection closed");
		};
            

	}
	
});