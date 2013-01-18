$(document).ready(function() {
	jQuery("#list2").jqGrid({
		url : 'jsonData.json?q=2',
		datatype : "json",
		height:300,
		autowidth : true,
		autoheight : true,
		colNames : ['Inv No', 'Date', 'Client', 'Amount', 'Tax', 'Total',
				'Notes'],
		colModel : [{
					name : 'id',
					index : 'id',
					width : 55
				}, {
					name : 'invdate',
					index : 'invdate',
					width : 90,
					align : 'left'
				}, {
					name : 'name',
					index : 'name asc, invdate',
					width : 100
				}, {
					name : 'amount',
					index : 'amount',
					width : 80
				}, {
					name : 'tax',
					index : 'tax',
					width : 80
				}, {
					name : 'total',
					index : 'total',
					width : 80
				}, {
					name : 'note',
					index : 'note',
					width : 150,
					sortable : false
				}],
		rowNum : 10,
		rowList : [10, 20, 30],
		pager : '#pager2',
		sortname : 'id',
		sortorder : "desc",
		loadonce: true,
		multiselect : true,
		multiselectWidth : 40,
		scrollOffset : 0
			// 滚动条偏移量
	});
	jQuery("#list2").jqGrid('navGrid', '#pager2', {
				edit : false,
				add : false,
				del : false
			});
});
