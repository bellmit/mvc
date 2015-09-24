/**
 * 自定义Grid 控件
 * 
 * gridId 控件id title 标题名称 dataUrl 数据的URL地址 chBox 是否出现checkbox列
 * 
 * @type
 */
var grids = {
	addGrid : function(gridId, titleName, dataUrl, chBox, headerArray,
			nameArray) {

		// 定义colModesl 属性对象
		function ColModesl(header, name, width, align) {
			this.header = header;
			this.name = name;
			// this.width = width;
			this.align = align;

		}
		// 把前端的数据拼接组成colmodes
		var _colModesls = new Array();
		$.each(headerArray, function(key, val) {
					var _colModesl = new ColModesl();
					_colModesl.header = val;
					_colModesl.name = nameArray[key];
					_colModesl.align = 'center';
					// _colModesl.width = 'fit';//自适应
					_colModesls[key] = _colModesl;
				});
		// 显示Grid
		gridId.omGrid({
					width : '90%',
					height : 450,
					limit : 14, // 分页显示，每页显示8条
					singleSelect : chBox, // 出现checkbox列，true 不出现
					dataSource : dataUrl,
					colModel : _colModesls
				});

		// 显示标题
		if (titleName != '') {
			gridId.omGrid({
						title : titleName
					});
		}
	}
}