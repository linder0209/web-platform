function showLabel(element, message) {
	var label = this.errorsFor(element);
	if (label.length) {
		// refresh error/success class
		label.removeClass(this.settings.validClass)
				.addClass(this.settings.errorClass);
	} else {
		// create label
		label = $("<" + this.settings.errorElement + "/>").attr({
					"for" : this.idOrName(element),
					generated : true
				}).addClass(this.settings.errorClass);
		if(!this.errorToolTip){
		    if($('div.errorToolTip').length > 0){
		    	this.errorToolTip = $('div.errorToolTip');
		    }else{
		    	this.errorToolTip = $('<div class="errorToolTip"><span></span></div>').appendTo('body');
		    }
		}
		var tip = this.errorToolTip;
		label.hover(function(){
		    var offset = $(this).offset(),
		    	width = $(this).width(),
		    	height = $(this).height();
		    tip.children().html(message || '');
			tip.css({
						top : offset.top - 5,
						left : offset.left + width + 5
					}).show();
		},function(){
			tip.hide();
		});
		if (this.settings.wrapper) {
			// make sure the element is visible, even in IE
			// actually showing the wrapped element is handled elsewhere
			label = label.hide().show()
					.wrap("<" + this.settings.wrapper + "/>").parent();
		}
		if (!this.labelContainer.append(label).length)
			this.settings.errorPlacement ? this.settings.errorPlacement(label,
					$(element)) : label.insertAfter(element);

	}
	if (!message && this.settings.success) {
		label.text("");
		typeof this.settings.success == "string" ? label
				.addClass(this.settings.success) : this.settings.success(label);
	}
	this.toShow = this.toShow.add(label);
}

$.validator.setDefaults({
    event:'blur',
    focusInvalid: false,//设为false，焦点不会自动定位到错误的输入框中
    onfocusout:function(element){$(element).valid();},
    ignore : ':hidden, :button',
	showErrors : function(errorMap, errorList) {
		for (var i = 0; this.errorList[i]; i++) {
			var error = this.errorList[i];
			this.settings.highlight
					&& this.settings.highlight.call(this, error.element,
							this.settings.errorClass, this.settings.validClass);
			showLabel.call(this, error.element, error.message);
		}
		if (this.errorList.length) {
			this.toShow = this.toShow.add(this.containers);
		}
		if (this.settings.success) {
			for (var i = 0; this.successList[i]; i++) {
				showLabel.call(this, this.successList[i]);
			}
		}
		if (this.settings.unhighlight) {
			for (var i = 0, elements = this.validElements(); elements[i]; i++) {
				this.settings.unhighlight.call(this, elements[i],
						this.settings.errorClass, this.settings.validClass);
			}
		}
		this.toHide = this.toHide.not(this.toShow);
		this.hideErrors();
		this.addWrapper(this.toShow).show();
	}
});