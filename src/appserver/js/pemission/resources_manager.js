Ext.onReady(function () {
    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'id', mapping:'id'} ,
        {name:'url', mapping:'url'}
    ]);

    var proxy = new Ext.data.HttpProxy({
        url:"../../CaPermissionAction_findByPages.action" ,
        timeout: 10*1000
    });

    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows"
    }, record);

    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy:proxy,
        reader:reader
    });

    store.load({
        params:{
            start:start, limit:pageSize
        }
    });

    var boxM = new Ext.grid.CheckboxSelectionModel({singleSelect:true});   //复选框单选

    var rowNumber = new Ext.grid.RowNumberer();         //自动编号

    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"标识", dataIndex:"id", align:'center',hidden:true,sortable:true, menuDisabled:true,sort:true} ,
        {header:"资源地址", dataIndex:"url", align:'center',sortable:true, menuDisabled:true},
        {header:'操作标记', dataIndex:'flag', align:'center',sortable:true, menuDisabled:true, renderer:show_flag, width:300}
    ]);

    var page_toolbar = new Ext.PagingToolbar({
        pageSize:pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    function setHeight() {
        var h = document.body.clientHeight - 8;
        return h;
    } ;

    var tb = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            {
                id:'add_resource.info',
                xtype:'button',
                text:'新增资源',
                iconCls:'add',
                handler:function () {
                    add_resource(grid_panel, store);
                }
            }]
    });

    var tbar = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            '资源地址', new Ext.form.TextField({
                name : 'url',
                id:'hzih.tbar.resource.url'
            }),
            {
                id:'tbar.tbar.resource.info',
                xtype:'button',
                iconCls:'select',
                text:'查询',
                handler:function () {
                    var url = Ext.getCmp('hzih.tbar.resource.url').getValue();
                    store.setBaseParam('url', url);
                    store.load({
                        params : {
                            start : start,
                            limit : pageSize
                        }
                    });
                }
            }]
    });

    var grid_panel = new Ext.grid.GridPanel({
        id:'grid.info',
        plain:true,
        height:setHeight(),
        viewConfig:{
            forceFit:true //让grid的列自动填满grid的整个宽度，不用一列一列的设定宽度。
        },
        bodyStyle:'width:100%',
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:true,
        cm:colM,
        sm:boxM,
        store:store,
        tbar : tb,
        listeners:{
            render:function(){
                tbar.render(this.tbar);
            }
        },
        bbar:page_toolbar
    });

    var port = new Ext.Viewport({
        layout:'fit',
        renderTo:Ext.getBody(),
        items:[grid_panel]
    });
});

function show_flag(value, p, r){
    return String.format(
        '<a id="delete_resource.info" href="javascript:void(0);" onclick="delete_resource();return false;" style="color: green;">删除</a>&nbsp;&nbsp;&nbsp;'  +
            '<a id="update_resource.info" href="javascript:void(0);" onclick="update_resource();return false;" style="color: green;">更新</a>&nbsp;&nbsp;&nbsp;'
    );
};

function delete_resource() {
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    if(!recode){
        Ext.Msg.alert("提示", "请选择一条记录!");
    }else{
        Ext.Msg.confirm("提示", "确认删除吗？", function(sid) {
            if (sid == "yes") {
                Ext.Ajax.request({
                    url : "../../CaPermissionAction_delete.action",
                    timeout: 20*60*1000,
                    method : "POST",
                    params:{id:recode.get("id")},
                    success : function(form, action) {
                        var respText = Ext.util.JSON.decode(form.responseText);
                        var msg = respText.msg;
                        Ext.Msg.alert("提示", msg);
                        grid_panel.getStore().reload();
                    },
                    failure : function(result) {
                        Ext.Msg.alert("提示", "删除失败!");
                    }
                });
            }
        });
    }
}  ;

function update_resource() {
    var grid_panel = Ext.getCmp("grid.info");
    var recode = grid_panel.getSelectionModel().getSelected();
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.TextField({
                fieldLabel : '资源地址',
                name : 'caPermission.url',
                value: recode.get('url'),
                id:  'caPermission.update.url',
                allowBlank : false,
                emptyText:"请输资源URL地址",
                blankText : "不能为空，请正确填写" ,
                listeners:{
                    blur:function(){
                        var url = this.getValue();
                        Ext.Ajax.request({
                            url : '../../CaPermissionAction_checkUrl.action',
                            timeout: 20*60*1000,
                            method : 'post',
                            params : {
                                url : url
                            },
                            success : function(r,o) {
                                var respText = Ext.util.JSON.decode(r.responseText);
                                var msg = respText.msg;
                                if (msg == 'false') {
                                    Ext.MessageBox.show({
                                        title:'信息',
                                        width:250,
                                        msg:'目标地址已存在,请更换！',
                                        buttons:Ext.MessageBox.OK,
                                        buttons:{'ok':'确定'},
                                        icon:Ext.MessageBox.INFO,
                                        closable:false,
                                        fn:function(e){
                                            if(e=='ok'){
                                                Ext.getCmp("caPermission.update.url").setValue('');
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                }
            })
        ]
    });
    var win = new Ext.Window({
        title:"更新地址",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'update_win.info',
                text:'更新',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../CaPermissionAction_modify.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            params:{
                                oldUrl:recode.get("url"),id:recode.get("id")
                            },
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'更新成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        grid_panel.getStore().reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'更新失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
} ;

function add_resource(grid_panel, store){
    var formPanel = new Ext.form.FormPanel({
        frame:true,
        autoScroll:true,
        labelWidth:150,
        labelAlign:'right',
        defaultWidth:300,
        autoWidth:true,
        layout:'form',
        border:false,
        defaults : {
            width : 250,
            allowBlank : false,
            blankText : '该项不能为空！'
        },
        items:[
            new Ext.form.DisplayField({
                fieldLabel : '提示',
                value:'请输入资源URL地址(允许通配一个*)'
            }),
            new Ext.form.TextField({
                fieldLabel : '资源地址',
                name : 'caPermission.url',
                id:  'caPermission.add.url',
                allowBlank : false,
                emptyText:"请输入要添加的资源URL地址",
                blankText : "不能为空，请正确填写",
                listeners:{
                    blur:function(){
                        var url = this.getValue();
                        var count = countInstances(url,"*");
                        if(count>1){
                            Ext.MessageBox.show({
                                title:'信息',
                                width:250,
                                msg:'当前只允许通配一个*号,请重新输入！',
                                buttons:Ext.MessageBox.OK,
                                buttons:{'ok':'确定'},
                                icon:Ext.MessageBox.INFO,
                                closable:false,
                                fn:function(e){
                                    if(e=='ok'){
                                        Ext.getCmp("caPermission.add.url").setValue('');
                                    }
                                }
                            });
                        } else{
                            Ext.Ajax.request({
                                url : '../../CaPermissionAction_checkUrl.action',
                                timeout: 20*60*1000,
                                method : 'post',
                                params : {
                                    url : url
                                },
                                success : function(r,o) {
                                    var respText = Ext.util.JSON.decode(r.responseText);
                                    var msg = respText.msg;
                                    if (msg == 'false') {
                                        Ext.MessageBox.show({
                                            title:'信息',
                                            width:250,
                                            msg:'目标地址已存在,请更换！',
                                            buttons:Ext.MessageBox.OK,
                                            buttons:{'ok':'确定'},
                                            icon:Ext.MessageBox.INFO,
                                            closable:false,
                                            fn:function(e){
                                                if(e=='ok'){
                                                    Ext.getCmp("caPermission.add.url").setValue('');
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }
            })
        ]
    });
    var win = new Ext.Window({
        title:"新增资源地址",
        width:500,
        layout:'fit',
        height:340,
        modal:true,
        items:formPanel,
        bbar:[
            '->',
            {
                id:'add_win.info',
                text:'新增',
                width:50,
                handler:function(){
                    if (formPanel.form.isValid()) {
                        formPanel.getForm().submit({
                            url :'../../CaPermissionAction_add.action',
                            timeout: 20*60*1000,
                            method :'POST',
                            waitTitle :'系统提示',
                            waitMsg :'正在连接...',
                            success : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'新增成功,点击返回页面!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.INFO,
                                    closable:false,
                                    fn:function(e){
                                        store.reload();
                                        win.close();
                                    }
                                });
                            },
                            failure : function() {
                                Ext.MessageBox.show({
                                    title:'信息',
                                    width:250,
                                    msg:'保存失败，请与管理员联系!',
                                    buttons:Ext.MessageBox.OK,
                                    buttons:{'ok':'确定'},
                                    icon:Ext.MessageBox.ERROR,
                                    closable:false
                                });
                            }
                        });
                    } else {
                        Ext.MessageBox.show({
                            title:'信息',
                            width:200,
                            msg:'请填写完成再提交!',
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.ERROR,
                            closable:false
                        });
                    }
                }
            },{
                text:'重置',
                width:50,
                handler:function(){
                    formPanel.getForm().reset();
                }
            }
        ]
    }).show();
};


function countInstances(mainStr, subStr)
{
    var count = 0;
    var offset = 0;
    do
    {
        offset = mainStr.indexOf(subStr, offset);
        if(offset != -1)
        {
            count++;
            offset += subStr.length;
        }
    }while(offset != -1)
    return count;
}







