
/**
 * 修改DataGrid对象的默认大小，以适应页面宽度
 * @author zt
 */

$.fn.extend({
	/**
	 * 修改DataGrid对象的默认大小，以适应页面宽度。
	 * 
	 * @param heightMargin
	 *            高度对页内边距的距离。
	 * @param widthMargin
	 *            宽度对页内边距的距离。
	 * @param minHeight
	 *            最小高度。
	 * @param minWidth
	 *            最小宽度。
	 * 
	 */
	resizeDataGrid : function(heightMargin, widthMargin, minHeight, minWidth) {
		var height = $(document.body).height() - heightMargin;
		var width = $(document.body).width() - widthMargin;

		height = height < minHeight ? minHeight : height;
		width = width < minWidth ? minWidth : width;

		$(this).datagrid('resize', {
			height : height,
			width : width
		});
	}
});

$(function() {
	// datagrid数据表格ID
	var datagridId = 'gridData';

	// 第一次加载时自动变化大小
	$('#' + datagridId).resizeDataGrid(20, 60, 600, 800);

	// 当窗口大小发生变化时，调整DataGrid的大小
	$(window).resize(function() {
		$('#' + datagridId).resizeDataGrid(20, 60, 600, 800);
	});
});
