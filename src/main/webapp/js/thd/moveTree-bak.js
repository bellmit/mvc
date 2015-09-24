(function ($) {
    function _$(d) {
        return $(d);
    }

    function renderMoveTree(t, isReload) {
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        if (data.options.cls != '') {
            movetree.addClass(data.options.cls)
        }
        $.ajax({
            url: data.options.url,
            dataType: 'json',
            type: 'POST',
            success: function (d, textStatus, jqXHR) {
                if (isReload) {
                    movetree.html('');
                }
                if ($.isArray(d)) {
                    loadMoveTree(t, d, isReload);
                } else {
                    clearMoveTree(t);
                }
                afterLoad(t);
            },
            error: function (jqXHR, textStatus, errorThrow) {
                alert(textStatus);
                clearMoveTree(t);
            }
        });
    }

    function afterLoad(t) {
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        movetree.addClass('tbc-movetree');
        if (data.options.isRetracted) {
            movetree.find('.move-tree-level-1').each(function () {
                var id = $(this).attr('data-id');
                retractedMoveTreeNode(t, id);
            })
        }
        data.options.onLoad.call(t);
    }

    function loadMoveTree(t, d, isReload) {
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        var level = 0;
        movetree.append(treeAnalysis(t, d, level));
        if (!isReload) {
            loadEvent(t);
        }
    }

    function loadEvent(t) {
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        movetree.click(function (e) {
            if (e.target == t) {
                movetree.find('.move-tree-node-selected').removeClass('move-tree-node-selected');
            }
        });
        movetree.delegate('.move-tree-node', 'click', function () {
            var id = $(this).attr('data-id');
            selectedMoveTreeNode(t, id);
        });
        movetree.delegate('.move-tree-node', 'mouseenter', function () {
            $(this).addClass('move-tree-node-hover');
        });
        movetree.delegate('.move-tree-node', 'mouseleave', function () {
            $(this).removeClass('move-tree-node-hover');
        });
        movetree.delegate('.move-tree-handle', 'click', function () {
            var id = $(this).parent().attr('data-id');
            retractedMoveTreeNode(t, id);
        });
        movetree.delegate('.move-tree-add', 'click', function (e) {
            e.preventDefault();
            var id = $(this).parent().parent().attr('data-id');
            addMoveTreeNodeHandle(t, id);
        });
        movetree.delegate('.move-tree-delete', 'click', function (e) {
            e.preventDefault();
            var id = $(this).parent().parent().attr('data-id');
            deleteMoveTreeNode(t, id);
        });
        movetree.delegate('.move-tree-edit', 'click', function (e) {
            e.preventDefault();
            var id = $(this).parent().parent().attr('data-id');
            var text = $(this).parent().parent().attr('data-text');
            editMoveTreeNodeHandle(t, id, text);
        });
        movetree.delegate('.move-tree-up', 'click', function (e) {
            e.preventDefault();
            var id = $(this).parent().parent().attr('data-id');
            upMoveTreeNode(t, id);
        });
        movetree.delegate('.move-tree-down', 'click', function (e) {
            e.preventDefault();
            var id = $(this).parent().parent().attr('data-id');
            downMoveTreeNode(t, id);
        });
    }

    function treeAnalysis(t, d, level) {
        var root = $('<ul>');
        $.each(d, function (k, v) {
            var li = addMoveTreeNodeLi(t, v, level);
            if (k == 0) {
                li.find('>.move-tree-node').addClass('move-tree-node-first');
            }
            if (k == d.length - 1) {
                li.find('>.move-tree-node').addClass('move-tree-node-last');
            }
            root.append(li);
        });
        return root;
    }

    function clearMoveTreeData(d) {
        d.id = d.id || null;
        d.text = d.text || null;
    }

    function retractedMoveTreeNode(t, id, isOpen) {
        var el = getMoveTreeNode(t, id);
        var root = el.parent();
        var ul = root.find('>ul');

        if (ul.length > 0) {
            ul.toggleClass('hide');
            el.toggleClass('move-tree-node-retracted');
            if (isOpen === true) {
                ul.removeClass('hide');
                el.removeClass('move-tree-node-retracted');
            }
            if (isOpen === false) {
                ul.addClass('hide');
                el.addClass('move-tree-node-retracted');
            }
        }
    }

    function selectedMoveTreeNode(t, id) {
        $('.move-tree-node-selected').removeClass('move-tree-node-selected');
        var el = getMoveTreeNode(t, id);
        el.toggleClass('move-tree-node-selected');
    }

    function addMoveTreeNodeHandle(t, id) {
        var data = $.data(t, 'movetree');
        if (!data.options.addUrl) {
            return;
        }
        var dialogO = $('#dialog');
        if (dialogO.length > 0) {
            dialogO.dialog("destroy");
        }
        $('.tbc-dialog').remove();
        if (dialogO.length < 1) {
            var dialog = $('<div id="dialog" class="tbc-dialog"></div>').appendTo('body');
            dialog.append('<div class="tbc-dialog-inner"></div>');
            var diglogInner = dialog.find('.tbc-dialog-inner');
            //load html
            var node = getMoveTreeNode(t, id);
            var offset = node.offset();
            var addBtn = node.find('.move-tree-add');
            var addBtnPosition = addBtn.offset();
            var nodeData = node.data('nodeData');
            var url = data.options.addUrl;
            if (!id || id == null || id == 'null') {
                id = ''
            }
            diglogInner.load(url, {id: id}, function () {
                if (dialog.length == 0) {
                    return;
                }
                dialog.data('nodeData', nodeData);
                dialog.data('node', node);
                dialog.data('dialogType', 'add');
                dialog.dialog({
                    title: '新增',
                    resizable: false,
                    width: 640,
                    position: [(addBtnPosition.left - 20), (offset.top + 34)],
                    modal: true,
                    draggable: false,
                    resizable: false,
                    modal: true
                });
                dialog.parent().addClass('tbc-dialog-outer')
            });
        }
    }

    function editMoveTreeNodeHandle(t, id, text) {
        var data = $.data(t, 'movetree');
        if (!data.options.editUrl) {
            return;
        }
        var dialogO = $('#dialog')
        if (dialogO.length > 0) {
            dialogO.dialog("destroy");
        }
        $('.tbc-dialog').remove();
        if (dialogO.length < 1) {
            var dialog = $('<div id="dialog" class="tbc-dialog"></div>').appendTo('body');
            dialog.append('<div class="tbc-dialog-inner"></div>');
            var diglogInner = dialog.find('.tbc-dialog-inner');
            //load html
            var node = getMoveTreeNode(t, id);
            var offset = node.offset();
            var addBtn = node.find('.move-tree-add');
            var addBtnPosition = addBtn.offset();
            if (addBtnPosition.left <= 0) {
                addBtnPosition = node.find('.move-tree-edit').offset();

            }
            var nodeData = node.data('nodeData');
            var url = data.options.editUrl;
            diglogInner.load(url, {id: id, text: text}, function () {
                if (dialog.length == 0) {
                    return;
                }
                dialog.data('nodeData', nodeData);
                dialog.data('node', node);
                dialog.data('dialogType', 'edit');
                dialog.dialog({
                    title: '编辑',
                    resizable: false,
                    width: 640,
                    position: [(addBtnPosition.left - 20), (offset.top + 34)],
                    modal: true,
                    draggable: false,
                    resizable: false,
                    modal: true
                });
                dialog.parent().addClass('tbc-dialog-outer')
            });
        }
    }

    function transferMoveTreeNode(t, id) {
        var dialog = $('#dialog');
        //load html
        var node = getMoveTreeNode(t, id);
        var nodeData = node.data('nodeData');
        var url = 'transfer.html';
        dialog.load(url, { 'id': nodeData.id }, function () {
            if (dialog.length == 0) {
                return;
            }
            dialog.data('nodeData', nodeData);
            dialog.data('node', $(t).parent().parent());
            dialog.dialog({
                resizable: false,
                height: 300,
                width: 400,
                modal: true,
                title: "调动"
            });
        });
    }

    function addMoveTreeNodeLi(t, v, level) {

        clearMoveTreeData(v);
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        var nt = '<div data-id=' + v.id + ' data-pm=' + v.canDisplay + ' data-text=' + v.text + '>';
        var node = $(nt);
        var nodeData = $.extend({}, v);
        nodeData.level = level;
        nodeData.movetree = movetree;
        if (nodeData.children) {
            delete nodeData.children;
        }
        node.addClass('move-tree-node move-tree-level-' + level);
        node.data('nodeData', nodeData);
        for (var i = 0; i < level; i++) {
            node.append('<div class="move-tree-space">');
        }
        node.append('<div class="move-tree-handle"></div>');
        node.append('<div class="move-tree-icon move-tree-icon-' + level + '">');
        node.append('<div class="move-tree-text">' + v.text + '</div>');
        var tool = ['<div class="move-tree-tool">',
            '<a href="#" class="move-tree-add">新增</a>',
            '<a href="#" class="move-tree-edit">编辑</a>',
            '<a href="#" class="move-tree-up">上移</a>',
            '<a href="#" class="move-tree-down">下移</a>',
            '<a href="#" class="move-tree-delete">删除</a>',

            '</div>'
        ];
        node.append(tool.join(''));
        node.append('<div class="clear"></div>');
        if (data.options.extool && data.options.extool.length > 0) {
            var ts = data.options.extool;
            for (var i = 0; i < ts.length; i++) {
                var too = $(ts[i]).attr('data-id', v.id);
                data.options.formatExtool.call(t, too, v);
                node.find('.move-tree-tool').append(too)
            }
        }
        var li = $('<li>');
        li.append(node);
        if (v.children && v.children.length > 0) {
            var newlevel = level + 1;
            li.append(treeAnalysis(t, v.children, newlevel));
        } else {
            node.addClass('move-tree-leaf');
        }
        return li;
    }

    function clearMoveTree(t) {
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        movetree.removeData('movetree');
    }

    function getMoveTreeNode(t, id) {
        var data = $.data(t, 'movetree');
        var movetree = data.movetree;
        var node = movetree.find('.move-tree-node[data-id=' + id + ']');
        return node;
    }

    function addMoveTreeNode(t, id, d) {
        var node = getMoveTreeNode(t, id);
        if (node.length == 0) {
            return;
        }
        var level = node.data('nodeData').level;
        var childroot = node.parent().find('>ul');
        var li = addMoveTreeNodeLi(t, d, level + 1);
        if (childroot.length > 0) {
            childroot.append(li);
            var pl = li.prev();
            if (pl.length > 0) {
                pl.find('>.move-tree-node').removeClass('move-tree-node-last');
                li.find('>.move-tree-node').addClass('move-tree-node-last');
            }
        } else {
            node.removeClass('move-tree-leaf');
            var ul = $('<ul>');
            ul.append(li);
            node.parent().append(ul);
            li.find('>.move-tree-node').addClass('move-tree-node-first move-tree-node-last');
        }
    }

    function checkDelete(t, id) {
        var data = $.data(t, 'movetree');
        $.ajax({
            url: data.options.deleteUrl,
            dataType: "json",
            data: {id: id, action: "check"},
            cache: false,
            type: "post",
            success: function (d) {
                if (d.success == true || d.success == 'true') {
                    exconfirm(t, id);
                } else {
                    errerMsg(t, d.msg);
                }
            },
            error: function (d) {
                errerMsg(t);
            }
        })
    }

    function exconfirm(t, id) {
        var data = $.data(t, 'movetree');
        var msg = data.options.exconfirmmsg;
        $.tbcmsg({
            msg: '<p class="movetree-confirm-msg">' + msg + '</p>',
            onOk: function () {
                trueDeleteNode(t, id);
            }
        });
    }

    function errerMsg(t, msgg) {
        var data = $.data(t, 'movetree');
        var msg = msgg || data.options.errormsg;
        $.tbcmsg({
            msg: '<p class="movetree-confirm-msg">' + msg + '</p>',
            onlyOk: true
        });

    }

    function trueDeleteNode(t, id) {
        var data = $.data(t, 'movetree');
        var node = getMoveTreeNode(t, id);
        if (node.length == 0) {
            return;
        }
        deleteAjax(t, id);

    }

    function trueDeleteNodeDom(t, id) {
        var data = $.data(t, 'movetree');
        var node = getMoveTreeNode(t, id);
        if (node.length == 0) {
            return;
        }
        var ul = node.parent().parent();
        var lis = ul.find('>li');
        if (lis.length > 1) {
            if (node.hasClass('move-tree-node-first')) {
                var next = node.parent().next();
                next.find('>.move-tree-node').addClass('move-tree-node-first');
            }
            if (node.hasClass('move-tree-node-last')) {
                var prev = node.parent().prev();
                prev.find('>.move-tree-node').addClass('move-tree-node-last');
            }
            node.parent().remove();
        } else if (lis.length == 1) {
            ul.parent().find('.move-tree-node').addClass('move-tree-leaf');
            ul.remove();
        }
    }

    function deleteAjax(t, id) {
        var data = $.data(t, 'movetree');
        var node = getMoveTreeNode(t, id);
        if (data.options.deleteUrl && data.options.deleteUrl != '') {
            $.ajax({
                url: data.options.deleteUrl,
                data: {id: id, action: "delete"},
                type: 'post',
                cache: false,
                dataType: "json",
                success: function (d) {
                    if (d.success == true || d.success == 'true') {
                        data.options.onDelete.call(t);
                        trueDeleteNodeDom(t, id);
                    } else {
                        errerMsg(t);
                    }
                },
                error: function () {
                    errerMsg(t);
                }
            })
        }
    }

    function getMessage(message, data) {
        if (data) {
            var res = message;
            for (var k in data) {
                res = res.replace(new RegExp("\\{" + k + "\\}", "g"), data[k]);
            }
            return res;
        }
    }

    function deleteMoveTreeNode(t, id) {
        var data = $.data(t, 'movetree');
        var node = getMoveTreeNode(t, id);
        if (node.length == 0) {
            return;
        }
        var nodeData = node.data('nodeData');
        var msg = getMessage(data.options.confirmmsg, {name: nodeData.text});
        $.tbcmsg({
            msg: '<p class="movetree-confirm-msg">' + msg + '</p>',
            onOk: function () {
                checkDelete(t, id);
            },
            onCancel: function () {

            }
        });
    }

    function updateMoveTreeNode(t, id, d) {
        var node = getMoveTreeNode(t, id);
        var nodeData = node.data('nodeData');
        clearMoveTreeData(d);
        $.extend(nodeData, d);
        node.attr('data-id', d.id);
        node.attr('data-text', d.text);
        node.find('.move-tree-text').html(d.text);
    }

    function moveHandleMoveTreeNode(t, id) {
        var node = getMoveTreeNode(t, id);
        var ul = node.parent().parent();
        var lis = ul.find('>li');
        if (lis.length == 1) {
            ul.parent().find('.move-tree-node').addClass('move-tree-leaf');
            return ul;
        } else if (lis.length > 1) {
            if (node.hasClass('move-tree-node-first')) {
                var next = node.parent().next();
                next.find('>.move-tree-node').addClass('move-tree-node-first');
            }
            if (node.hasClass('move-tree-node-last')) {
                var prev = node.parent().prev();
                prev.find('>.move-tree-node').addClass('move-tree-node-last');
            }
        }

    }

    function moveMoveTreeNode(t, pid, cid) {
        var pnode = getMoveTreeNode(t, pid);
        var cnode = getMoveTreeNode(t, cid);
        if (pnode.length == 0 || cnode.length == 0) {
            return;
        }
        var plevel = pnode.data('nodeData').level;
        var remainder = moveHandleMoveTreeNode(t, cid);
        var root = pnode.parent();
        var ul = root.find('>ul');
        if (ul.length > 0) {
            ul.append(cnode.parent());
            cnode.parent().prev().find('>.move-tree-node').removeClass('move-tree-node-last');
            cnode.removeClass('move-tree-node-first');
            cnode.addClass('move-tree-node-last');
        } else {
            pnode.removeClass('move-tree-leaf');
            var cul = $('<ul>');
            cul.append(cnode.parent());
            pnode.parent().append(cul);
            cnode.addClass('move-tree-node-first move-tree-node-last');
        }
        if (remainder && ul[0] != remainder[0]) {
            remainder.remove();
        }
        adjustMoveTreeLevel(t, cid, plevel + 1);
    }

    function adjustMoveTreeLevel(t, id, level) {
        var node = getMoveTreeNode(t, id);
        var nodeData = node.data('nodeData');
        var oldv = nodeData.level;
        if (oldv == level) {
            return;
        }
        $.extend(nodeData, {level: level});
        node.removeClass('move-tree-level-' + oldv);
        node.addClass('move-tree-level-' + level);
        node.find('.move-tree-space').remove();
        for (var i = 0; i < level; i++) {
            node.prepend('<div class="move-tree-space">');
        }
        var root = node.parent();
        var ul = root.find('>ul');
        if (ul.length > 0) {
            ul.find('>li').each(function () {
                var id = $(this).find('>div.move-tree-node').attr('data-id');
                adjustMoveTreeLevel(t, id, level + 1);
            });
        }
    }

    function upMoveTreeNode(t, id) {
        var node = getMoveTreeNode(t, id);
        var li = node.parent();
        node.removeClass('');
        var prev = li.prev();
        var pnode = prev.find('>.move-tree-node');
        if (pnode.hasClass('move-tree-node-first')) {
            pnode.removeClass('move-tree-node-first');
            node.addClass('move-tree-node-first');
        }
        if (node.hasClass('move-tree-node-last')) {
            node.removeClass('move-tree-node-last');
            pnode.addClass('move-tree-node-last');
        }
        prev.before(li);
        handleMoveNode(t, id, "up");
    }

    function downMoveTreeNode(t, id) {
        var node = getMoveTreeNode(t, id);
        var li = node.parent();
        node.removeClass('');
        var next = li.next();
        var pnode = next.find('>.move-tree-node');
        if (pnode.hasClass('move-tree-node-last')) {
            pnode.removeClass('move-tree-node-last');
            node.addClass('move-tree-node-last');
        }
        if (node.hasClass('move-tree-node-first')) {
            node.removeClass('move-tree-node-first');
            pnode.addClass('move-tree-node-first');
        }
        next.after(li);
        handleMoveNode(t, id, "down");
    }

    function handleMoveNode(t, id, type) {
        var data = $.data(t, 'movetree');

        if (data.options.moveUrl && data.options.moveUrl != '') {
            $.ajax({
                url: data.options.moveUrl,
                data: {id: id, type: type},
                type: 'post',
                success: function () {
                    data.options.onMove.call(t);
                }
            })
        }
    }

    $.fn.movetree = function () {
        if (typeof arguments[0] == "string") {
            return $.fn.movetree.methods[arguments[0] ](this, arguments[1]);
        }
        var o = arguments[0] || {};
        return this.each(function () {
            var data = $.data(this, "movetree");
            var newData = '';
            if (data) {
                newData = $.extend(data.options, o);
                if (data.isLoaded) {

                }
            } else {

                newData = $.extend({}, $.fn.movetree.defaults, $.fn.movetree.parseOptions(this), o);
                data = $.data(this, "movetree", {
                    options: newData,
                    movetree: _$(this),
                    isLoaded: false
                });
            }
            renderMoveTree(this);
        });

    };

    $.fn.movetree.methods = {
        clear: function (t) {
            clearMoveTree(t[0]);
        },
        addNode: function (t, res) {
            addMoveTreeNode(t[0], res.nodeId, res.data);
        },
        deleteNode: function (t, nodeId) {
            deleteMoveTreeNode(t[0], nodeId);
        },
        updateNode: function (t, res) {
            updateMoveTreeNode(t[0], res.nodeId, res.data);
        },
        moveNode: function (t, res) {
            moveMoveTreeNode(t[0], res.parentId, res.nodeId);
        },
        switchNode: function (t, res) {
            retractedMoveTreeNode(t[0], res.nodeId, res.isOpen);
        },
        refresh: function (t) {
            renderMoveTree(t[0], true);
        }
    };
    $.fn.movetree.parseOptions = function (el) {
        var t = $(el);
        return {};
    };
    $.fn.movetree.defaults = {
        width: 'auto',
        height: 'auto',
        cls: '',
        data: null,
        url: null,
        addNode: true,
        deleteNode: true,
        upNode: true,
        downNode: true,
        extool: [],
        confirmmsg: '确定要删除{name}吗?',
        exconfirmmsg: '删除后将无法恢复,确定要删除吗?',
        errormsg: '无法删除',
        onDelete: function () {
        },
        onMove: function () {
        },
        onLoad: function () {
        },
        formatExtool: function () {
        },
        isRetracted: true,
        renderTo: ''
    };
})(jQuery);
