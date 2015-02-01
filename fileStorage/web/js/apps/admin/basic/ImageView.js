var ImageModel = Backbone.Model.extend({
	urlRoot: window.app.api + 'image',
	defaults: {
		"id": null,
		"name": "Nový obrázek",
		"small": null,
		"medium": null,
		"large": null,
		"original": null
	},
	name: "Terminál",
	fields: {
		"name": {title: "Název", type: "string"},
		"small": {title: "Malý obrázek", type: "none"},
		"medium": {title: "Střední obrázek", type: "none"},
		"large": {title: "Velký obrázek", type: "none"},
		"original": {title: "Původní obrázek", type: "file"}
	}
});

var FileModel = Backbone.Model.extend({
	defaults: {
		"id": null,
		"fileName": null,
		"filePath": null,
		"fileSize": null,
		"mimeType": null
	}
});


var ImageCollection = Backbone.Collection.extend({
	url: window.app.api + 'image',
    model: ImageModel
});

var ImageView = Backbone.View.extend({

	name: "Obrázky",
	fieldPre: 		"image_form_",
	fieldHashPre: 	"#image_form_",
	
	model: null, 
	modelClass: "ImageModel",
	collection: null,
	
	form: null,
	
	file: null,

	initialize: function(){	
		this.model = new window[this.modelClass];
		this.createForm();
		this.initUploadEvents();
	
		this.collection = new ImageCollection();
		this.collection.on("sync", this.render, this);
		this.collection.fetch();
	},
	
	destroy: function(){
		this.remove();
	},
	
	render: function(){		
		this.$el.empty();		
		this.$el.append("<h1>" + this.name + "</h1>");	
		
		// přidat
		var thisObject = this;
		this.$el.append(
			$("<button>Přidat položku</button>").button({
				icons: {
					primary: "ui-icon-plus"
				}
			}).click(function(){
				thisObject.editItem(null);
			})
		);
		
		// editační formulář
		this.$el.append(this.form);
		this.form.hide();
		this.initUploadButtons();
		
		// seznam
		var holder = $("<div />", {class: "imageListHolder"});
		thisObject.$el.append(holder);	
		this.collection.each(function(item){
			
			var div = $("<div />", {class: "imageListItem"});
			
			var iddiv = $("<div />", {class: "listItemId"});
			iddiv.append("ID: " + item.get("id"));
			
			var txt = $("<div />", {class: "listItemName"});
			txt.append(item.get("name"));
			
			var img = $("<div />", {class: "listItemImage"});
			img.css("background-image", "url('"+window.app.files + item.get("original").filePath+"')");
			
			var editButton = $('<a>', { text: "Upravit", title: "Upravit", href: "#", class: "listEdit" }).click(function(){
				thisObject.editItem(item.get("id"));
			});
			var deleteButton = $('<a>', { text: "Smazat", title: "Smazat", href: "#", class: "listDelete" }).click(function(){
				thisObject.deleteItem(item.get("id"));
			});
			
			
			
			//
			div.append(iddiv);
			div.append(txt);
			div.append(img);
			div.append(editButton);
			div.append(deleteButton);
			
			//
			holder.append(div);	
		});
	},
	
	createForm: function(){
		this.form = $("<div />", {class: "imageformholder"});
		
		// layout
		var third1 = $("<div />", {id: "uploadThird1", class: "third"});
		var third2 = $("<div />", {id: "uploadThird2", class: "third"});
		var third3 = $("<div />", {id: "uploadThird3", class: "third"});		
		
		// otevírací tagy
		var html = "";
		html += "<form class='entityForm'>\n";
			
		// položky
		var title;
		var type;
		for( id in this.model.fields ){
			title = this.model.fields[id].title;
			type = this.model.fields[id].type;
			
			if(type == "none"){ continue; }				
			
			if(type != "file"){
				html += "<div class='labelLike'>"+title+"</div>";	
				html += "<input type='"+type
							+"' name='"+this.fieldPre+id
							+"' id='"+this.fieldPre+id
							+"' value='' class='text ui-widget-content ui-corner-all' />";
				continue;
			}		
			
			// file
			html += "<label for='fileInputField' class='fileInputLabel'>Upload obrázku</label>";	
			html += "<input type='"+type+"'"
						+" name='fileInputField'"
						+" id='fileInputField'"
						+" class='fileInputField' />";							
		}			
			
		// zavírací tagy
		//html += "<input type='submit' tabindex='-1'>\n";
		html += "</form>\n";			
		third1.append(html);
		third1.append("<div id='imageButtonsHolder'></div>");
		
		// upload area + náhled nového
		third2.append("<div class='labelLike'>Náhled nového obrázku</div>");
		third2.append("<div id='prewImageDiv'></div>");		
		
		// náhled starého
		third3.append("<div class='labelLike'>Aktuální obrázek</div>");
		third3.append("<div id='origImageDiv'></div>");
		
		this.form.append(third1);
		this.form.append(third2);
		this.form.append(third3);
		this.form.append("<br style='clear: left;' />");
	},
	
	initUploadButtons: function(){
	
		// 
		$("#imageButtonsHolder").empty();
		
		
		// procházet
		var thisObject = this;
		$("#imageButtonsHolder").append(
			$("<button id='imgBrowse'>Procházet..</button>").button({
				icons: {
					primary: "ui-icon-folder-open"
				}
			}).click(function(){
				$("#fileInputField").click();
			})
		);
		$("#imageButtonsHolder").append("<br /><br />");
		
		// storno
		var thisObject = this;
		$("#imageButtonsHolder").append(
			$("<button id='imgStorno'>Storno</button>").button({
				icons: {
					primary: "ui-icon-closethick"
				}
			}).click(function(){
				thisObject.form.slideUp();
			})
		);
		
		// uložit
		var thisObject = this;
		$("#imageButtonsHolder").append(
			$("<button>Uložit</button>").button({
				icons: {
					primary: "ui-icon-check"
				}
			}).click(function(){
				thisObject.copyAndSendData()
			})
		);
		
		
	},
	
	initUploadEvents: function(){
		
		// výběr souboru
		var thisObject = this;
		$("html").on("change", "#fileInputField", function(evt){
			
			//console.log("File changed to: " + $(evt.target).val());
			//console.log($(evt.target));
			//thisObject.previewFile($(evt.target));
			thisObject.setNewFile($(evt.target)[0].files[0]);	
		});
		
		// tažení souboru
		$("html").on("dragover", function(evt){
			$("#prewImageDiv").addClass("dragIn");
			evt.preventDefault();
			evt.stopPropagation();
		});
		$("html").on("dragenter", function(evt){
			$("#prewImageDiv").addClass("dragIn");
			evt.preventDefault();
			evt.stopPropagation();
		});
		$("html").on("dragout", function(evt){
			$("#prewImageDiv").removeClass("dragIn");
			evt.preventDefault();
			evt.stopPropagation();
		});
		$("html").on("dragleave", function(evt){
			$("#prewImageDiv").removeClass("dragIn");
			evt.preventDefault();
			evt.stopPropagation();
		});
		$("html").on("drop", function(evt){
			if(evt.originalEvent.dataTransfer){
				if(evt.originalEvent.dataTransfer.files.length) {
					console.log("File drop");
					evt.preventDefault();
					evt.stopPropagation();
					/*UPLOAD FILES HERE*/
					console.log(evt.originalEvent.dataTransfer.files);
					
					//$("#fileInputField")[0].files[0] = evt.originalEvent.dataTransfer.files[0];
					//$("#fileInputField").change();
					
					thisObject.setNewFile(evt.originalEvent.dataTransfer.files[0]);				
				}   
			}
		});
	},
	
	setNewFile: function(newFile){
		this.file = newFile;
		this.previewFile(newFile);
	},
	
	previewFile: function(file){

        if( file ){
		
			console.log("Preview file");
			console.log(file);
			
			$('#prewImageDiv').removeClass("dragIn");
			$('#prewImageDiv').addClass("imageIn");
			$('#prewImageDiv').text("");
		
            var reader = new FileReader();            
            reader.onload = function (e) {
                $('#prewImageDiv').css("background-image", "url('"+e.target.result+"')");
            }    
			reader.readAsDataURL(file);
			
		}else{
			console.log("Preview failed - bad args");
		}
	},
	
	deleteItem: function(id){
		console.log("Delete item: " + id);
		
		this.model = this.collection.get(id);
		console.log(this.model);
		
		thisObject = this;
		this.model.destroy({success: function(model, response) {
			thisObject.collection.fetch();
		}});
	},
	
	editItem: function(id){
		console.log("Edit item: " + id);
		this.copyDataAndEdit(id);
	},
	
	// data do formuláře
	copyDataAndEdit: function(mid){
		
		// načteme z kolekce model nebo uděláme nový
		if( mid == null){
			this.model = new window[this.modelClass]; 
		}else{
			this.model = this.collection.get(mid);
		}
		
		// smažem obrázky
		$("#prewImageDiv").css("background", "");
		$("#prewImageDiv").text("Žádný obrázek, tažením vložte nový");
		$("#origImageDiv").css("background", "");
		$("#origImageDiv").text("Žádný obrázek");
		this.file = null;
		
		
		// původní obrázek
		if(this.model.get("original") != null){
			$('#origImageDiv').text("");
			$('#origImageDiv').css("background-image", "url('"+window.app.files + this.model.get("original").filePath+"')");
		}
		
		// zkopíruje data
		for( id in this.model.fields ){
		
			// podle typu
			switch(this.model.fields[id].type) {
				case "number":
					$(this.fieldHashPre+id).val(
						this.model.get(id)
					);
					console.log("Field: " + id 
						+ " Value: " + $(this.fieldHashPre+id).val()
						+ " Model: " + this.model.get(id)
					);
					break;
				case "checkbox":
					console.log(this.fieldHashPre+id);
					$(this.fieldHashPre+id)[0].checked = this.model.get(id);
					console.log("Field: " + id 
						+ " Value: " + $(this.fieldHashPre+id)[0].checked
						+ " Model: " + this.model.get(id)
					);
					break;
				case "select":
					if(this.model.get(id) != null){
						$(this.fieldHashPre+id).val(
							this.model.get(id).id
						);
						console.log("Field: " + id 
							+ " Value: " + $(this.fieldHashPre+id).val()
							+ " Model: " + this.model.get(id).id
						);
					}
					break;
				case "none":
					break;
				case "file":
					break;
				default:
					$(this.fieldHashPre+id).val(
						this.model.get(id)
					);
					console.log("Field: " + id 
						+ " Value: " + $(this.fieldHashPre+id).val()
						+ " Model: " + this.model.get(id)
					);
			} 
		}
		
		// otevře form
		this.form.slideDown();
	},
	
	// data z formuláře
	copyAndSendData: function(){
	
		// musíme mít soubor
		if(this.file == null){
			return;
		}
	
		// zkopíruje data
		for( id in this.model.fields ){
		
			// podle typu
			switch(this.model.fields[id].type) {
				case "number":
					this.model.set(id, parseFloat($(this.fieldHashPre+id).val()));
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id).val());
					break;
				case "checkbox":
					this.model.set(id, $(this.fieldHashPre+id)[0].checked);
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id)[0].checked);
					break;
				case "select":
					var selID = parseFloat($(this.fieldHashPre+id).val());
					this.model.set(id, {id: selID});
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id).val());
					break;
				case "none":
					break;
				case "file":
					break;
				default:
					this.model.set(id, $(this.fieldHashPre+id).val());
					console.log("Field: "+id+" Value: "+$(this.fieldHashPre+id).val());
			} 
		}
		
		// log
		console.log("Values read from form");
		console.log(this.model);
		
		// uděláme form data
		var formData = new FormData();
		formData.append('name', this.model.get("name"));
		
		// přidáme file
		formData.append("file", this.file);
		
		// přidáme id
		if(this.model.get("id") == null){
			formData.append("id", -1);
		}else{
			formData.append("id", this.model.get("id"));
		}
		
		// odešleme ajax
		var thisObject = this;
		$.ajax({
			url : window.app.root + "upload",
			type : "POST",
			data : formData,
			cache : false,
			contentType : false,
			processData : false,
			success : function(data, textStatus, jqXHR) {
				console.log("Ajax OK");
				console.log("data: " + data);
				console.log("textStatus: " + textStatus);
				thisObject.form.slideUp();
				thisObject.collection.fetch();
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("Ajax ERROR");
				console.log("textStatus: " + textStatus);
				console.log("errorThrown: " + errorThrown);
			}
		});
	}
	
});