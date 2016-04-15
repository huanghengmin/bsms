/**
 * 角色管理
 */
Ext.onReady(function() {

    Ext.BLANK_IMAGE_URL = '../../js/ext/resources/images/default/s.gif';
    Ext.QuickTips.init();
    Ext.form.Field.prototype.msgTarget = 'side';

    var start = 0;
    var pageSize = 15;
    var record = new Ext.data.Record.create([
        {name:'id',			mapping:'id'},
        {name:'name',			mapping:'name'},
        {name:'description',			mapping:'description'},
        {name:'createdTime',			mapping:'createdTime'},
        {name:'modifiedTime',		mapping:'modifiedTime'} ,
        {name:'status',		mapping:'status'}
    ]);
    var proxy = new Ext.data.HttpProxy({
        url:"../../CaRoleAction_findByPages.action"
    });
    var reader = new Ext.data.JsonReader({
        totalProperty:"total",
        root:"rows",
        id:'id'
    },record);
    var store = new Ext.data.GroupingStore({
        id:"store.info",
        proxy : proxy,
        reader : reader
    });

    var boxM = new Ext.grid.CheckboxSelectionModel();   //复选框
    var rowNumber = new Ext.grid.RowNumberer();         //自动 编号
    var colM = new Ext.grid.ColumnModel([
        boxM,
        rowNumber,
        {header:"角色名",			dataIndex:"name",		      align:'center',sortable:true,menuDisabled:true},
        {header:"描述信息",		dataIndex:"description",	  align:'center',menuDisabled:true},
//        {header:'创建时间',		dataIndex:'createdTime',	  align:'center',sortable:true,menuDisabled:true},
//        {header:'修改时间',		dataIndex:'modifiedTime', align:'center',sortable:true,menuDisabled:true,renderer:show_null},
        {header:'访问状态',		dataIndex:'status', align:'center',sortable:true,menuDisabled:true,renderer:show_status},
        {header:'操作标记',		dataIndex:'flag', align:'center',sortable:true,menuDisabled:true,renderer:show_flag}

    ]);
    var page_toolbar = new Ext.PagingToolbar({
        pageSize : pageSize,
        store:store,
        displayInfo:true,
        displayMsg:"显示第{0}条记录到第{1}条记录，一共{2}条",
        emptyMsg:"没有记录",
        beforePageText:"当前页",
        afterPageText:"共{0}页"
    });

    var tbar = new Ext.Toolbar({
        autoWidth :true,
        autoHeight:true,
        items:[
            '角色名称', new Ext.form.TextField({
                name : 'role_name',
                id:'hzih.tbar.role_name'
            }),
            {
                id:'tbar.tbar.role_name.info',
                xtype:'button',
                iconCls:'select',
                text:'查询',
                handler:function () {
                    var role_name = Ext.getCmp('hzih.tbar.role_name').getValue();
                    store.setBaseParam('role_name', role_name);
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
        width:setWidth(),
        animCollapse:true,
        loadMask:{msg:'正在加载数据，请稍后...'},
        border:false,
        collapsible:false,
        cm:colM,
        sm:boxM,
        store:store,
        stripeRows:true,
        autoExpandColumn:2,
        disableSelection:true,
        bodyStyle:'width:100%',
        enableDragDrop:true,
        selModel:new Ext.grid.RowSelectionModel({singleSelect:true}),
        viewConfig:{
            forceFit:true,
            enableRowBody:true,
            getRowClass:function(record,rowIndex,p,store){
                return 'x-grid3-row-collapsed';
            }
        },
        tbar:[
//            new Ext.Button({
//                id:'btnAdd.info',
//                text:'新增',
//                iconCls:'add',
//                handler:function(){
//                    add_role(grid_panel,store);     //连接到 新增 面板
//                }
//            })
        ],
        listeners:{
            render:function(){
                tbar.render(this.tbar);
            }
        },
        view:new Ext.grid.GroupingView({
            forceFit:true,
            groupingTextTpl:'{text}({[values.rs.length]}条记录)'
        }),
        bbar:page_toolbar
    });
    var port = new Ext.Viewport({
        layout:'fit',
        renderTo: Ext.getBody(),
        items:[grid_panel]
    });
    store.load({
        params:{
            start:start,limit:pageSize
        }
    });
});
function setHeight(){
    var h = document.body.clientHeight-8;
    return h;
}

function setWidth(){
    return document.body.clientWidth-8;
}

function show_null(value){
    if(value == 'null'){
        return '';
    } else {
        return value;
    }
}

function show_status(value, p, r){
    if(r.get("status")=="0"){
        return String.format('<img src="../../img/icon/ok.png" alt="允许访问" title="允许访问" />');
    }else if(r.get("status")=="1"){
        return String.format('<img src="../../img/icon/off.gif" alt="禁止访问" title="禁止访问" />');
    }
}

function show_flag(value, p, r){
    if(r.get("status")=="0"){
        return String.format('<a id="stop.info" href="javascript:void(0);" onclick="stop();return false;" style="color: green;">禁止</a>&nbsp;&nbsp;&nbsp;');
    }else if(r.get("status")=="1"){
        return String.format('<a id="allow.info" href="javascript:void(0);" onclick="allow();return false;" style="color: green;">允许</a>&nbsp;&nbsp;&nbsp;');
    }
}

function stop(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var store = grid.getStore();
    Ext.MessageBox.show({
        title:'信息',
        msg:'<font color="green">确定禁止角色访问？</font>',
        width:260,
        buttons:Ext.Msg.YESNO,
        buttons:{'ok':'确定','no':'取消'},
        icon:Ext.MessageBox.INFO,
        closable:false,
        fn:function(e){
            if(e == 'ok'){
                var myMask = new Ext.LoadMask(Ext.getBody(),{
                    msg : '正在更新,请稍后...',
                    removeMask : true
                });
                myMask.show();
                Ext.Ajax.request({
                    url : '../../CaRoleAction_stop.action',             // 删除 连接 到后台
                    params :{id:recode.get("id")},
                    method:'POST',
                    success : function(r,o){
                        myMask.hide();
                        var respText = Ext.util.JSON.decode(r.responseText);
                        var msg = respText.msg;
                        Ext.MessageBox.show({
                            title:'信息',
                            width:250,
                            msg:msg,
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.INFO,
                            closable:false,
                            fn:function(e){
                                if(e=='ok'){
                                    grid.render();
                                    store.reload();
                                }
                            }
                        });
                    }
                });
            }
        }
    });
}

function allow(){
    var grid = Ext.getCmp('grid.info');
    var recode = grid.getSelectionModel().getSelected();
    var store = grid.getStore();
    Ext.MessageBox.show({
        title:'信息',
        msg:'<font color="green">确定要允许用户访问？</font>',
        width:260,
        buttons:Ext.Msg.YESNO,
        buttons:{'ok':'确定','no':'取消'},
        icon:Ext.MessageBox.INFO,
        closable:false,
        fn:function(e){
            if(e == 'ok'){
                var myMask = new Ext.LoadMask(Ext.getBody(),{
                    msg : '正在更新,请稍后...',
                    removeMask : true
                });
                myMask.show();
                Ext.Ajax.request({
                    url : '../../CaRoleAction_allow.action',             // 删除 连接 到后台
                    params :{id:recode.get("id")},
                    method:'POST',
                    success : function(r,o){
                        myMask.hide();
                        var respText = Ext.util.JSON.decode(r.responseText);
                        var msg = respText.msg;
                        Ext.MessageBox.show({
                            title:'信息',
                            width:250,
                            msg:msg,
                            buttons:Ext.MessageBox.OK,
                            buttons:{'ok':'确定'},
                            icon:Ext.MessageBox.INFO,
                            closable:false,
                            fn:function(e){
                                if(e=='ok'){
                                    grid.render();
                                    store.reload();
                                }
                            }
                        });
                    }
                });
            }
        }
    });
}

















