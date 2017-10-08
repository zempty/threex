<script>
    layui.use('layer', function() {
        layui.$('#demo').click(function() {
            layer.confirm('你确定要删除XXXX吗?', {
                icon: 3,
                title: 'win10风格提示框',
                skin: 'layer-ext-winconfirm'
            }, function(index) {
                layer.msg('点击了确定');
                layer.close(index);
            });
            //替换窗体的关闭图标
            layui.$('.layer-ext-winconfirm').find('.layui-layer-close').html(
                '<i class="layui-icon">ဆ<i>');
            //移除移动窗体的div
            layui.$('.layui-layer-move').remove();
            //移除最小化最大化关闭按钮a标签的href属性
            layui.$('.layer-ext-winconfirm').find('.layui-layer-setwin').children('a').removeAttr(
                'href');
        });
    });
</script>