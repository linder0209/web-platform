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
        		
        		this.markersArray = new $.everbridge.platform.Collection(),//marker collection
        		this.markerId = 1;
        		this.infoWindow = new google.maps.InfoWindow(); // info window object
				this.geocoder = new google.maps.Geocoder();
				this.geocodingLanguage = {
		            en : 'en'
		        };
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
	                    //autoOpen : false,
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
             * @param: regions Array
             * regions = [{
					city:'MILTON',
					country_code:'AU',
					country_name:'AUSTRALIA',
					ip:'1.1.1.1',
					lat:-35.3167,
					lng:150.433,
					region:'QUEENSLAND'
				}]
 			  * @param : isRemove The default is true
             */
            addMarker : function(regions, isRemove) {
                if(isRemove !== false){
                	this.removeAllMarker();
                }
				if(!$.isArray(regions)){
					regions = [regions];
				}
				var me = this;
				$(regions).each(function(index,item){
					var latlng = new google.maps.LatLng(item.lat,item.lng);
					var marker = new google.maps.Marker({
		                    position : latlng
		                });
		            marker.setMap(me.customMap);
		            marker.setTitle(item.ip);
		            //me.markersArray[me.getMarkerId()] = marker;
		            me.markersArray.add({
                        id : me.getMarkerId(),
                        marker : marker
                    });
				});
				
				this.setBounds();
				this.setInfoWindow();
            },
            
            removeMarker : function(marker){
            	if (marker) {
					marker.setMap(null);
					this.markersArray.remove(marker);
				}
            },
            
            removeAllMarker : function(){
            	var markers = this.markersArray;
            	if (markers) {
            	    var fn = function(item, index, len){
            	    	item.marker.setMap(null);
            	    };
                    this.markersArray.removeAll();
				}
            },
            
            // automatically adjusting the viewport to fit all markers
	    	setBounds : function() {
		        var bounds = new google.maps.LatLngBounds(),
		        	markers = this.markersArray,
		        	marker,
		        	len = markers.getCount();
				
				if(len == 1){
				    marker = markers.itemAt(0).marker;
					var southWest = new google.maps.LatLng((marker
                                    .getPosition().lat() - 10.000000),
                            (marker.getPosition().lng() - 10.000000));
                    var eastNorth = new google.maps.LatLng((marker
                                    .getPosition().lat() + 10.000000),
                            (marker.getPosition().lng() + 10.000000));

                    bounds = new google.maps.LatLngBounds(southWest, eastNorth);
				}else if (len > 1) {
				    for (var i = 0; i < len; i++) {
						bounds.extend(markers.items[i].marker.getPosition());
                    }
            	}
		
		        this.customMap.fitBounds(bounds);
		    },
		    
		    // Add an overlay that looks like a bubble for a marker 
		    setInfoWindow : function() {
                var me = this, 
                	markers = this.markersArray,
                	marker,
                	clickFn = function(event){
                		var marker = this;
                		var markerId = me.markersArray.indexOf(marker),
                		me.setBubble.call(me, markerId);
                	};
            
                for (var i = 0, len = markers.items.length; i < len; i++) {
                	marker = markers.itemAt(i);
		            google.maps.event.addListener(marker.marker, 'click', clickFn);
		        }
		        google.maps.event.addListener(me.infoWindow, 'closeclick',
                        function() {
                            var infoClose = document.getElementById('close').value;
//                            if (infoClose == 'close') {
//                                me.removeAllMarker();
//
//                                for (var i in t.markerDataHash) {
//                                    t.addMarker(t.markerDataHash[i].ip,
//                                            t.mapSize.LARGEMAP);
//                                }
//                                t.setInfoWindow();
//                            }
                        });
            },
		    
		    editByDrag : function(markerId) {
		    	var me = this, 
                	marker = this.findMarker(markerId).marker;
                if(marker){
                	marker.setDraggable(true);
                	this.infoWindow.close();
                	this.toggleBounce(marker);
                	google.maps.event.addListener(marker,
                            'dragend', function() {
                                var latlng = new google.maps.LatLng(this
                                                .getPosition().lat(), this
                                                .getPosition().lng());
                                this.setDraggable(false);
                                me.adjustLatLng(latlng, this, markerId);
                            });
                }
            },
            
            editByAddress : function(markerId) {
				var me = this;
		        var tmplData = $.tmpl('editMarkerByAdress', {
		                });
				tmplData.find('a.addressSubmit').click(function(e){
					e.preventDefault();
					me.codeAddress(markerId, $(this).prev().val());
				});
		        this.infoWindow.setContent(tmplData[0]);
				
				//To add this code avoid that the input isn't focus.
		        setTimeout(function() {
		            $('#address').parent().parent().parent().css('z-index', 1002), $('.infowindow_hidden_scrollbar')
		                    .parent().parent().css('overflow', 'hidden')
		        }, 100);
		    },
		    
		    codeAddress : function(markerId, value) {
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

                                    me.setLocationResult(results[0], markerId,
                                            false);

                                    me.infoWindow.open(me.customMap, marker);

                                } else {
                                    alert("Geocode was not successful for the following reason: "
                                            + status);
                                }
                            });
                }
            },
    
		    adjustLatLng : function(latlng, currentMarker, markerId) {
                var me = this;
                this.geocoder.geocode({
                            'latLng' : latlng,
                            'language' : this.geocodingLanguage.en
                        }, function(results, status) {
                            if (status == google.maps.GeocoderStatus.OK) {
                                if (results[0]) {
                                    me.setLocationResult(results[0], markerId,
                                            true);

                                    me.infoWindow.open(me.customMap, currentMarker);
                                }
                            } else {
                                alert('Geocoder failed due to: ' + status);
                            }
                        });
                this.currentMarker = currentMarker;
            },
    		
    		setLocationResult : function(result, markerId, draggble) {
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
					var tempData = $.tmpl('bubbleMarker',{
									markerId : markerId
								});
					// add click event
					this.addEditEvent(tempData);
			
			        this.infoWindow.setContent(tempData[0]);
				}else{
					var tempData = $.tmpl('editMarkerConfirm',{
								});
					tempData.find('a.answerNo').click(function(e){
						e.preventDefault();
						//me.answerNo();
					});
					tempData.find('a.answerYes').click(function(e){
						e.preventDefault();
						//me.answerYes();
					});
			        this.infoWindow.setContent(tempData[0]);
				}
		    },
    
    		// set marker animation
		    toggleBounce : function(marker) {
		        marker.setAnimation(marker.getAnimation() != null
		                ? null
		                : google.maps.Animation.BOUNCE);
		    },
		    
		    findMarker : function(markerId){
		    	return this.markersArray.key(markerId);
		    },
		    
		    getMarkerId : function(){
		    	return 'markerId_' + this.markerId ++;
		    },
		    
		    addEditEvent : function(element){
		    	var me = this;
		    	element.find('a.editByAddress').click(function(e){
					e.preventDefault();
					me.editByAddress($(this).attr('markerId'));
				});
				element.find('a.editByDrag').click(function(e){
					e.preventDefault();
					me.editByDrag($(this).attr('markerId'));
				});
		    },
		    
		    answerNo : function(drag) {
                if (drag == "drag") {
                    this.deleteMarkers(this.markersArrayLargeMap);

                    for (var i in this.markerDataHash) {
                        this.addMarker(this.markerDataHash[i].ip,
                                this.mapSize.LARGEMAP);
                    }
                    this.setInfoWindow();
                }

                this.newMarker.setMap(null);
                this.newMarker = null;
                this.setBounds(this.mapSize.LARGEMAP);

            },

            answerYes : function() {
                var markerIp = document.getElementById("markerIp").value; // IP
                                                                            // of
                                                                            // the
                // marker
                // which has
                // been
                // clicked

                // reset IP's properties
                this.markerDataHash[markerIp].city = document
                        .getElementById("city").value;
                this.markerDataHash[markerIp].region = document
                        .getElementById("region").value;
                this.markerDataHash[markerIp].country_name = document
                        .getElementById("countryName").value;
                this.markerDataHash[markerIp].country_code = document
                        .getElementById("countryCode").value;
                this.markerDataHash[markerIp].lat = this.newMarker
                        .getPosition().lat();
                this.markerDataHash[markerIp].lng = this.newMarker
                        .getPosition().lng();

                // empty all the markers on small map
                this.deleteMarkers(this.markersArray);

                // empty all the markers on large map
                this.deleteMarkers(this.markersArrayLargeMap);

                // redraw all the markers on large and small map
                for (var i in this.markerDataHash) {
                    this.addMarker(this.markerDataHash[i].ip,
                            this.mapSize.LARGEMAP);
                    this.addMarker(this.markerDataHash[i].ip,
                            this.mapSize.SMALLMAP);
                }

                this.setInfoWindow();

                this.info.close();

                this.newMarker.setMap(null);
                this.newMarker = null;

                this.setBounds(this.mapSize.LARGEMAP);
                this.setBounds(this.mapSize.SMALLMAP);

                this.saveData(markerIp);
            },

            saveData : function(ip) {
                var newData = this.markerDataHash[ip];

                CF_Common.Ajax.ajax({
                            url : "/gmaps",
                            data : {
                                'newData' : newData
                            },
                            dataType : 'json',
                            success : function(data) {
                                if (data && data.length > 0) {

                                }
                            }
                        })
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
	                           
            	}
            },
            
            
            setBubble : function(markerId) {
                var tempData = $.tmpl('bubbleMarker', {
                            markerId : markerId
                        });
                // add click event
                this.addEditEvent(tempData);

                this.infoWindow.setContent($(tempData)[0]);
                this.infoWindow.open(this.customMap, this);
                this.currentMarker = marker.marker;
                setTimeout(function() {
                            $('.infowindow_hidden_scrollbar').parent().parent()
                                    .css('overflow', 'hidden')
                        }, 100);
            },
            
            findMarkerId : function(marker){
            	var markers = this.markersArray,
            		markerId = 
            	markers.each(function(item,i,len){
            		if(item.marker == marker){
            			
            			return false;
            		}
            	});
            }
        }
    });
    
    common.gmaps.initialize = function(config) {
        return new common.gmaps(config);
    }

})(EB_Common);
