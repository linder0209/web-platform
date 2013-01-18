$(function(){
		var gmaps = EB_Common.gmaps,
		    mapsInst = gmaps.initialize({
		    	lngId : 'coordinateY',
		    	latId : 'coordinateX'
		    });
		
		$('#mapInfo').click(function(){
		    mapsInst.createMap();
		    //坐标最大值为180
		    var lat = $('#coordinateX').val(),
		    	lng = $('#coordinateY').val();
		    lat = parseFloat(lat, 10) || 10;
		    lng = parseFloat(lng, 10) || 10;
		    
		    var regions = {
		    	city:"MILTON",
				country_code:"AU",
				country_name:"AUSTRALIA",
				ip:"1.1.1.1",
				lat:lat,
				lng:lng,
				region:"QUEENSLAND"
		    };
		    mapsInst.openMap();
			mapsInst.addMarker(regions, latlng);
		});
	});