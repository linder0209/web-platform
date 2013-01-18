jQuery(document).ready(function(){


jQuery("#listsg11").jqGrid({
   	/*url:'server.php?q=1',
	datatype: "xml",*/
	datatype:'local',
	height: 190,
   	colNames:['Inv No','Date', 'Client', 'Amount','Tax','Total','Notes'],
   	colModel:[
   		{name:'id',index:'id', width:55},
   		{name:'invdate',index:'invdate', width:90},
   		{name:'name',index:'name', width:100},
   		{name:'amount',index:'amount', width:80, align:"right"},
   		{name:'tax',index:'tax', width:80, align:"right"},		
   		{name:'total',index:'total', width:80,align:"right"},		
   		{name:'note',index:'note', width:150, sortable:false}		
   	],
   	rowNum:8,
   	rowList:[8,10,20,30],
   	pager: '#pagersg11',
   	sortname: 'id',
    viewrecords: true,
    sortorder: "desc",
	multiselect: false,
	subGrid: true,
	caption: "Grid as Subgrid",
	subGridRowExpanded: function(subgrid_id, row_id) {
		// we pass two parameters
		// subgrid_id is a id of the div tag created whitin a table data
		// the id of this elemenet is a combination of the "sg_" + id of the row
		// the row_id is the id of the row
		// If we wan to pass additinal parameters to the url we can use
		// a method getRowData(row_id) - which returns associative array in type name-value
		// here we can easy construct the flowing
		var subgrid_table_id, pager_id;
		subgrid_table_id = subgrid_id+"_t";
		pager_id = "p_"+subgrid_table_id;
		$("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
		jQuery("#"+subgrid_table_id).jqGrid({
			/*url:"subgrid.php?q=2&id="+row_id,
			datatype: "xml",*/
			datatype:'local',
			colNames: ['No','Item','Qty','Unit','Line Total'],
			colModel: [
				{name:"num",index:"num",width:80,key:true},
				{name:"item",index:"item",width:130},
				{name:"qty",index:"qty",width:70,align:"right"},
				{name:"unit",index:"unit",width:70,align:"right"},
				{name:"total",index:"total",width:70,align:"right",sortable:false}
			],
		   	rowNum:20,
		   	pager: pager_id,
		   	sortname: 'num',
		    sortorder: "asc",
		    height: '100%'
		});
		
		var mydata = [{
					num : "1",
					item : "2007-10-01",
					qty : "test",
					unit : "note",
					total : "200.00"
				}];
		for (var i = 0; i <= mydata.length; i++){
			jQuery("#"+subgrid_table_id).jqGrid('addRowData', i + 1, mydata[i]);
		}
		
		jQuery("#"+subgrid_table_id).jqGrid('navGrid',"#"+pager_id,{edit:false,add:false,del:false})
	},
	subGridRowColapsed: function(subgrid_id, row_id) {
		// this function is called before removing the data
		//var subgrid_table_id;
		//subgrid_table_id = subgrid_id+"_t";
		//jQuery("#"+subgrid_table_id).remove();
	}
});

var mydata1 = [{
			id : "1",
			invdate : "2007-10-01",
			name : "test",
			amount : "note",
			tax : "200.00",
			total:'total',
			note:'note'
		}];
for (var i = 0; i <= mydata1.length; i++){
	jQuery("#listsg11").jqGrid('addRowData', i + 1, mydata1[i]);
}
jQuery("#listsg11").jqGrid('navGrid','#pagersg11',{add:false,edit:false,del:false});

});