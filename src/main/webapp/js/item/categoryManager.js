$(document).ready(function () {
    var url = $('#jsonForm').attr('action') + '/system/organization/orgTreeJson';
    $('#tt').tree({
        /**
         * 获取数据源
         */
        url: url,
        /**
         * 选择树节点触发事件
         */
        onSelect: function (node) {
            // 返回树对象
            var tree = $(this).tree;
            // 选中的节点是否为叶子节点,如果不是叶子节点,清除选中
            var isLeaf = tree('isLeaf', node.target);
            if (!isLeaf) {
                // 清除选中
                alert('不能选择机构1!');
                $('#combotree').combotree('clear');
            }
        },
        onContextMenu: function (e, node) {
            e.preventDefault();
            $(this).tree('select', node.target);
            $('#mm').menu('show', {
                left: e.pageX,
                top: e.pageY
            });
        }
    });

    $('#moveTreeId').movetree({
        cls: 'ems-itempool-manage',
        url: url,
        addUrl: 'addCategory',
        editUrl: '${ctx!}/html/exambase/exambase.toEditBaseCategory.do',
        deleteUrl: '${ctx!}/html/exambase/exambase.deleteBaseCategory.do',
        moveUrl: 'action.php',
        errormsg: '<@i18n key="ems.includBaseCantDelete"/>',
        onLoad: function () {
            $('.ems-itempool-manage .move-tree-add').css('display', 'inline-block');
            $('.ems-itempool-manage .move-tree-delete').css('display', 'inline-block');
            $('.ems-itempool-manage').find('.move-tree-tool:first').find('.move-tree-delete').hide();
            $('.ems-itempool-manage-authorization:first').hide();
            $('.move-tree-node').each(function () {
                var permissions = $(this).attr('data-pm')
                if (permissions == 'false') {
                    $(this).find('.move-tree-tool').remove();
                }
            })
        }
    });
});

var category = {
    addCategory: function () {
        alert("新增类别");
    },
    removeCategory: function () {
        alert("删除类别");
    },
    dialogCancel: function (dialogId) {
        $('#' + dialogId).dialog('close');
    }
};