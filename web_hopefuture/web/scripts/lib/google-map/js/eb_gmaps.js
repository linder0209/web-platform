/**
 * google map
 * 
 * @author Linder Wang
 * @date : 2012-11-19
 */
(function(common) {

    // constructor for google map
    common.gmaps = function(config) {
        this.settings = $.extend(true, {}, common.gmaps.defaults, config);
        this.init();
    }

    $.extend(common.gmaps, {
        defaults : {
            defaultMapCenter : new google.maps.LatLng(-34.397, 150.644),
            zoom : 8,
            disableDefaultUI : true,
            panControl : true,
            zoomControl : true,
            mapTypeId : google.maps.MapTypeId.ROADMAP
        },

        prototype : {
        
        	init : function(){
        		this.infoWindow = new google.maps.InfoWindow(); // info window object
				this.geocoder = new google.maps.Geocoder();
				this.geocodingLanguage = {
		            en : 'en'
		        };
		        
		        // Add an overlay that looks like a bubble for a marker
		        var me = this;
		        google.maps.event.addListener(this.infoWindow, 'closeclick',
                        function() {
                            var infoClose = document.getElementById('close').value;
                            if (infoClose == 'close') {
                                me.removeMarker(me.movedMarker);
								delete me.movedMarker;
								me.setBounds();
                            }
                        });
            
        		//init template
        		var htmlTmpl = '<div style="overflow: auto;">'
						+ '<fieldset class="infowindow_hidden_scrollbar">'
						+	'<legend>Current Location</legend>'
						+	'<div class="ip_title">Location Name:</div>'
						+	'<div class="ip_content">${locationName}</div>'
						+	'<div class="ip_title">Country:</div>'
						+	'<div class="ip_content">${country}</div>'
						+	'<div class="ip_title">Address:</div>'
						+	'<div class="ip_content">${address}</div>'
						+	'<div class="ip_title">Apt/Suite/Unit:</div>'
						+	'<div class="ip_content">${suite}</div>'
						+	'<div class="ip_title">State/Province:</div>'
						+	'<div class="ip_content">${state}</div>'
						+	'<div class="ip_title">Postal Code:</div>'
						+	'<div class="ip_content">${postalCode}</div>'
						+	'<input type="hidden" value="13.13.13.13" id="markerIp">'
						+	'<input type="hidden" value="" id="close">'
						+'</fieldset>'
						+'<div class="Location_correct">'
						+	'<strong>Location not correct?</strong><br /> '
						+	'<a class="editByAddress" markerId="${markerId}" href="#">Enter address to change it.</a><br /> <em>or</em><br /> '
						+	'<a class="editByDrag" markerId="${markerId}" href="#">Drag it to a new location</a>'
						+'</div>'
					    +'</div>';
				
				$.template('bubbleMarker', htmlTmpl);
				
				htmlTmpl = '<div style="overflow: auto;">'
							+ '<fieldset class="infowindow_hidden_scrollbar">'
							+ '	<legend>Edit Location</legend>'
							+ '	<em>Example: city, country</em>'
							+ '	<input type="hidden" value="" id="close">'
							+ '	<input type="text" style="width: 100%;" name="address" id="address" />' 
							+ '	<a class="addressSubmit" href="#">Submit</a>'
							+ '</fieldset>'
						+ '</div>';
				$.template('editMarkerByAdress', htmlTmpl);
				
				htmlTmpl = '<div style="overflow: auto;">'
							+ '	<fieldset class="infowindow_hidden_scrollbar">'
							+ '		<strong>Is this the correct location?</strong><br>'
							+ '		<br> <input type="hidden" value="2.2.2.2" id="markerIp">'
							+ '		<input type="hidden" value="" id="city"> <input type="hidden"'
							+ '			value="西西里" id="region"> <input type="hidden" value="意大利"'
							+ '			id="countryName"> <input type="hidden" value="IT"'
							+ '			id="countryCode"> <input type="hidden" value="close"'
							+ '			id="close">'
							+ '		<div class="ip_title">City:</div>'
							+ '		<div class="ip_content"></div>'
							+ '		<div class="ip_title">Region:</div>'
							+ '		<div class="ip_content">西西里</div>'
							+ '		<div class="ip_title">Country:</div>'
							+ '		<div class="ip_content">意大利</div>'
							+ '	</fieldset>'
							+ '	<a class="answerYes" href="#">Yes,that it is.</a><br />'
							+ ' <a class="answerNo" href="#">No, take me back.</a>'
							+ '</div>';
				$.template('editMarkerConfirm', htmlTmpl);
        	},
        	
            // create map
            createMap : function() {
            	if(!this.customMap){
            		var me = this;
            		$('#map_canvas').remove();
	                $('body')
	                        .append('<div id="map_canvas" style="width:1000px; height:600px"></div>');
	
	                var settings = this.settings;
	                var mapOptions = {
	                    zoom : settings.zoom,
	                    center : settings.defaultMapCenter,
	                    disableDefaultUI : settings.disableDefaultUI,
	                    panControl : settings.panControl,
	                    zoomControl : settings.zoomControl,
	                    mapTypeId : settings.mapTypeId
	                };
	
	                this.customMap = new google.maps.Map(document
	                                .getElementById('map_canvas'), mapOptions);
	
	                $('#map_canvas').dialog({
	                    width : 1000,
	                    height : 600,
	                    resizable : false,
	                    modal : true,
	                    draggable : true,
	                    close : function(event, ui){
	                    	me.closeGoogleMap();
	                    }
	                });
            	}
            },
			
			openMap : function(){
				$('#map_canvas').dialog('open');
			},
			
            // add market
            /*
             * @param: region Array
             * region = {
					city:'MILTON',
					country_code:'AU',
					country_name:'AUSTRALIA',
					ip:'1.1.1.1',
					lat:-35.3167,
					lng:150.433,
					region:'QUEENSLAND'
				}
 			  * @param : isAddress When the value is true, we create marker for address, otherwise for latlng.The default is false.
             */
            addMarker : function(region, isAddress) {
            	var loadMarker = function(latlng) {
                    marker = new google.maps.Marker({
                                position : latlng
                            });
                    marker.setMap(this.customMap);
                    marker.setTitle(region.ip);
                    this.currentMarker = marker;
                    this.setBounds();
                    this.setMarkerEvent();
                }
                if (isAddress === true) {
                    var me = this;
                    this.geocoder.geocode({
                                'address' : region.address,
                                'language' : this.geocodingLanguage.en
                            }, function(results, status) {
                                if (status == google.maps.GeocoderStatus.OK) {
                                	loadMarker(results[0].geometry.location);
                                } else {
                                    alert("Geocode was not successful for the following reason: "
                                            + status);
                                }
                            });
                } else {
	            	var latlng = new google.maps.LatLng(region.lat,region.lng);
					loadMarker(latlng);
	            }
            },
            
            removeMarker : function(marker){
            	if (marker) {
					marker.setMap(null);
				}
            },
            
            // automatically adjusting the viewport to fit all markers
	    	setBounds : function() {
                var bounds, 
                    marker = this.currentMarker, 
                    southWest, 
                    eastNorth;

                southWest = new google.maps.LatLng(
                        (marker.getPosition().lat() - 10.000000), (marker
                                .getPosition().lng() - 10.000000));
                eastNorth = new google.maps.LatLng(
                        (marker.getPosition().lat() + 10.000000), (marker
                                .getPosition().lng() + 10.000000));
                bounds = new google.maps.LatLngBounds(southWest, eastNorth);
                this.customMap.fitBounds(bounds);
            },
		    
		    setMarkerEvent : function(){
		        var me = this,
		            marker = this.currentMarker,
                	clickFn = function(event){
                		var currentMarker = this;
                		me.setBubble.call(me, currentMarker);
                	};
		    	google.maps.event.addListener(marker, 'click', clickFn);
		    },
		    
		    
            setBubble : function(marker) {
            	var me = this,
            	    tempData = $.tmpl('bubbleMarker', {
                        });
                // add click event
				tempData.find('a.editByAddress').click(function(e){
					e.preventDefault();
					me.editByAddress();
				});
				tempData.find('a.editByDrag').click(function(e){
					e.preventDefault();
					me.editByDrag();
				});
                this.infoWindow.setContent($(tempData)[0]);
                this.infoWindow.open(this.customMap, marker);
                setTimeout(function() {
                            $('.infowindow_hidden_scrollbar').parent().parent()
                                    .css('overflow', 'hidden')
                        }, 100);
            },
            
		    editByDrag : function() {
		    	var me = this, 
                	marker = this.currentMarker;
                if(marker){
                	marker.setDraggable(true);
                	this.infoWindow.close();
                	this.toggleBounce(marker);
                	google.maps.event.addListener(marker,
                            'dragend', function() {
                                this.setDraggable(false);
                                me.adjustLatLng(this);
                            });
                }
            },
            
            // set marker animation
		    toggleBounce : function(marker) {
		        marker.setAnimation(marker.getAnimation() != null
		                ? null
		                : google.maps.Animation.BOUNCE);
		    },
		    
		    adjustLatLng : function(marker) {
                var me = this;
                var latlng = new google.maps.LatLng(marker.getPosition().lat(),
                        marker.getPosition().lng());
                this.geocoder.geocode({
                            'latLng' : latlng,
                            'language' : this.geocodingLanguage.en
                        }, function(results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                if (results[0]) {
                                    me.setLocationResult(results[0], marker, true);
                                }
                            } else {
                                alert('Geocoder failed due to: ' + status);
                            }
                        });
            },
		    
            editByAddress : function() {
				var me = this;
		        var tmplData = $.tmpl('editMarkerByAdress', {
		                });
				tmplData.find('a.addressSubmit').click(function(e){
					e.preventDefault();
					me.setAddress($(this).prev().val());
				});
		        this.infoWindow.setContent(tmplData[0]);
				
				//To add this code avoid that the input isn't focus.
		        setTimeout(function() {
		            $('#address').parent().parent().parent().css('z-index', 1002), $('.infowindow_hidden_scrollbar')
		                    .parent().parent().css('overflow', 'hidden')
		        }, 100);
		    },
		    
		    setAddress : function(value) {
                var me = this;
                if (this.geocoder) {
                    this.geocoder.geocode({
                                'address' : value,
                                'language' : this.geocodingLanguage.en
                            }, function(results, status) {
                                if (status == google.maps.GeocoderStatus.OK) {
                                    me.customMap
                                            .setCenter(results[0].geometry.location);
                                    var marker = new google.maps.Marker({
                                                map : me.customMap,
                                                position : results[0].geometry.location
                                            });
                                    me.movedMarker = marker; 
                                    me.setLocationResult(results[0], marker,
                                            false);
                                } else {
                                    alert("Geocode was not successful for the following reason: "
                                            + status);
                                }
                            });
                }
            },
    
    		setLocationResult : function(result, marker, draggble) {
		        var me = this,
		        	city = '',
		        	region = '',
		        	countryName = '',
		        	countryCode = '',
		        	address_components = result.address_components,
		        	len = address_components.length; 
		
		        for (var i = 0; i < len; i++) {
		            if (address_components[i].types[0] == 'locality')
		                city = address_components[i].long_name;
		
		            if (address_components[i].types[0] == 'administrative_area_level_1'
		                    || address_components[i].types[0] == 'administrative_area_level_2')
		                region = address_components[i].long_name;
		
		            if (address_components[i].types[0] == 'country')
		                countryName = address_components[i].long_name;
		
		            if (address_components[i].types[0] == 'country')
		                countryCode = address_components[i].short_name;
		        }
		
				if(draggble){
				    this.setBubble(marker);
				}else{
					this.setPromptBubble(marker);
				}
		    },
    
    		 
            setPromptBubble : function(marker) {
                var me = this, 
                    tempData = $.tmpl('editMarkerConfirm', {});
                tempData.find('a.answerNo').click(function(e) {
                    e.preventDefault();
                        me.cancel();
                    });
                tempData.find('a.answerYes').click(function(e) {
                    e.preventDefault();
                        me.ok();
                    });
                this.infoWindow.setContent(tempData[0]);

                this.infoWindow.setContent($(tempData)[0]);
                this.infoWindow.open(this.customMap, marker);
            },
            
		    cancel : function() {
				this.removeMarker(this.movedMarker);
				delete this.movedMarker;
				
				this.infoWindow.close();
				this.setBounds();
            },

            ok : function() {
                this.removeMarker(this.currentMarker);
                this.currentMarker = this.movedMarker;
                delete this.movedMarker;
                this.infoWindow.close();

                this.setBounds();
                this.setMarkerEvent();
            },

            closeGoogleMap : function(){
            	if(this.currentMarker){
            		var lngId = this.settings.lngId,
            			latId = this.settings.latId;
            		//longitude 
            		var lng = this.currentMarker.getPosition().lng();
            		//latitude 
            		var lat = this.currentMarker.getPosition().lat();
	                
	                $('#' + lngId).val(lng);
	                $('#' + latId).val(lat);
	                this.removeMarker(this.currentMarker);            
            	}
            }
            
        }
    });
    
    common.gmaps.initialize = function(config) {
        return new common.gmaps(config);
    }

})(EB_Common);
