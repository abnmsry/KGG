<script setup>

</script>

<template>

</template>

<style scoped>

</style>


<!DOCTYPE html> <html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta charset="utf-8"/>
  <title>jokes</title>
</head>

<style>
  #login_background{ background-image: url("img.jpg"); background-position: center center; background-repeat: no-repeat; background-attachment: fixed; background-size: cover;};
</style>

<body id="login_background">
<div id="test" style="text-align:center; margin-top: 100px;">
  <input type="button" value="test" class="layui-btn layui-btn-radius" @click="test" style="width: 200px; height: 100px; font-size: 30px;">
</div>
</body>
<link rel="stylesheet" href="./layui/css/layui.css" media="all">
<script src="layui/layui.js">

</script>
<script src="vue.js">

</script>
<script src="axios.min.js">

</script>

<script>
  layui.use(['layer', 'form'], function(){
    var layer = layui.layer;
    var form = layui.form;
  });
  var test = new Vue({ el: "#test",
    data: { "message": "test" },
    methods:{ test:function(){ that = this;
      axios.post("test.py", {params:{ID:123, NAME:"test"}}).then(
        function(response){
          console.log(response);
          that.message = response.data;
          layui.use(["layer"], function(){
            var layer = layui.layer; layer.alert(that.message)
          })
        }) .catch(function(error){
          that.message = "try again";
          layui.use(["layer"], function(){
            var layer = layui.layer;
            layer.alert(that.message)
          })
        })
    }
  }
  })
</script>



