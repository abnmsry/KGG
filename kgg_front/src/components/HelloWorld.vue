<template>
  <div>
    <h1>Paper Recommendation Systems</h1>
    <div class="searchmain">
      <p>Search: <input type="text" v-model="message" placeholder="" class="searchinput" /> </p>
      <select v-model="selected" class="searchtype" @change="changeselected($event)">
        <option disabled value=""> Please select one</option>
        <option>Title|Abstract</option>
        <option>Author</option>
      </select>
<!--      <div>{{search}}</div>-->
    </div>
    <div class="main">
      <div class="container" id="kg">
      </div>
      <div class="recommend">
        <div class="recommendHeader">
          <el-button @click="recommendation" color="#BCCAD7" class="recommendButton">Recommendation</el-button>
          <el-input v-model="input" placeholder="5" ></el-input>
        </div>
        <div class="results"></div>
      </div>
    </div>
    <div class="information">
    </div>
  </div>

</template>


<script>
  import * as d3 from 'd3'
  // import * as d3plus from 'd3plus'
  import {transform} from "../../test/unit/jest.conf";
  // import { computed } from 'vue'
  // import {message} from "../../test/e2e/custom-assertions/elementCount" //for message
export default {
  name: 'HelloWorld',
  data() {
    return {
      recommendpaper:[],
      input:"",
      searchnode:{},
      // recom:false,
      updated_dy:"0em",
      message: "", //initial value
      selected: "",
      width: 800,
      height: 600,
      colorNodeList: ['#E8AF44', '#72B4ED'],
      colorLinkList: ['#D08D10', '#0871CB','#9C42AE'],
      testGraph: {
        "nodes":[],
        "links": []
      }
    }
  },
  computed: {
    selectInp: {
      get() {
        return this.selected
      },
      set() {
      }
    }
  }, //修改search的值
  created() {
    // this.queryRelatedNode([this.message])
    // this.getGraphData([this.message])
  },
  watch: {
    message(newVal,oldVal){
      this.request(newVal);
    }
  },
  methods: {

    request(newVal){
      clearTimeout(this.timer);
      this.timer=setTimeout(()=>{
        if (this.message===""){
          var _this=this
          _this.testGraph["nodes"]=[];
          _this.testGraph["links"]=[];
          _this.initGraph(_this.Graph)
        }
        if(this.message.trim()!==""){
          var _this=this
          if (_this.selected==="Title|Abstract"){
            _this.getPaperData(newVal)
          }else if (_this.selected==="Author"){
            _this.getAuthorData(newVal)
          }
        }
      },300);
    },

    getAuthorData(authorkeys) {
      var _this = this;
      _this.testGraph["nodes"]=[];
      _this.testGraph["links"]=[];
      var req =_this.axios.get("/api/author/authorkey/" + authorkeys)
        .then(function (response) {
          console.log(response)
          if (response.data.length>1){
            _this.testGraph["nodes"] = response.data.slice(response.data.length-25) //默认只保留25个结果
          }else if (response.data.length===1){
            _this.testGraph["nodes"].push(...response.data)
          }
        })
        .catch(function (error) {
          console.log(error)
        });
      Promise.all([req]).then(() => {
        _this.initGraph(_this.testGraph)
      })
    },

    getPaperData(paperkeys) {
      var _this = this;
      _this.testGraph["nodes"]=[];
      _this.testGraph["links"]=[];
      var req=_this.axios.get("/api/paper/paperkey/" + paperkeys)
        .then(function (response) {
          if (response.data.length>1){
            _this.testGraph["nodes"] = response.data.slice(response.data.length-25) //默认只保留25个结果
          }else if (response.data.length===1){
            _this.testGraph["nodes"].push(...response.data)
          }
        })
        .catch(function (error) {
          console.log(error)
        });
      Promise.all([req]).then(() => {

        _this.initGraph(_this.testGraph)
      })
    },

    clicknode(d){
      var _this=this;
      _this.testGraph["nodes"] = [];
      _this.testGraph["links"] = [];
      if (d.group===1) {
        //get citations
        var req1 = _this.axios.get("/api/paper/paperIDcitation/" + d.id)
          .then(function (response) {
            var resdata = response.data;
              _this.testGraph["nodes"].push(...resdata);
              for (var i = 0; i < (resdata.length); i++) {
                _this.testGraph["links"].push({"source": resdata[i].id, "target": d.id, "group": 1,"strength":1});
              }
          })
          .catch(function (error) {
            console.log(error)
          });

        //get cited paper
        var req2 = _this.axios.get("/api/paper/paperIDcited/" + d.id)
          .then(function (response) {
            var resdata = response.data;
              _this.testGraph["nodes"].push(...resdata);
              for (var i = 0; i < (resdata.length); i++) {
                _this.testGraph["links"].push({"source": d.id, "target": resdata[i].id, "group": 1,"strength":1});
              }
          })
          .catch(function (error) {
            console.log(error)
          })


        var req3 = _this.axios.get("/api/paper/paperIDauthor/" + d.id)
          .then(function (response) {
            var resdata = response.data;
              _this.testGraph["nodes"].push(...resdata);
              for (var i = 0; i < (resdata.length); i++) {
                _this.testGraph["links"].push({"source":resdata[i].id , "target": d.id, "group": 2,"strength":1});
              }
          })
          .catch(function (error) {
          })


        Promise.all([req1, req2, req3]).then(() => {

          _this.testGraph["nodes"].push({"id": d.id, "prop": d.prop, "group": 1})

          _this.initGraph(_this.testGraph)
        })
      }else if (d.group===2){
        var req1 = _this.axios.get("/api/author/authorIDpaper/" + d.id)
          .then(function(response){
            var resdata=response.data;
            _this.testGraph["nodes"].push(...resdata);
            for(var i=0;i<(resdata.length);i++){
              _this.testGraph["links"].push({"source":d.id,"target":resdata[i].id,"group":2,"strength":1});
            }
          })
          .catch(function(error){
            console.log(error)
          });

        Promise.all([req1]).then(() => {

          _this.testGraph["nodes"].push({"id":d.id,"prop":d.prop,"group":2})

          console.log(_this.testGraph)

          _this.initGraph(_this.testGraph)
        })
      }
    },

    initGraph(data) {


      d3.select(".container").selectAll("svg").remove() //remove previous picture

      var _this = this;

      const nodes = data.nodes.map(d => Object.create(d));
      const links = data.links.map(d => Object.create(d));
      const types = Array.from(new Set(links.map(d => d.group)))


      let simulation = d3.forceSimulation(nodes)
        .force("link", d3.forceLink(links).id(d => d.id).strength(d => d.source.group === d.target.group ? 1.5 : 0.1))
        .force("collide", d3.forceCollide().radius(() => 40).strength(0.3))
        .force("charge", d3.forceManyBody().strength(-30))
        .force("center", d3.forceCenter(_this.width / 2, _this.height / 2));



      const svg = d3.select(".container")
        .append("svg")
        .attr("height", 650)
        .attr("width", 800)  //调整KG的大小和位置
        .attr("ViewBox", [0, 0, _this.width, _this.height])
        //放缩
        .call(d3.zoom().on("zoom", function () {
          g.attr("transform", d3.event.transform)
        }))

      let legend = svg.append("g")
        .attr("id", "legend");

      // 定义图例中的色块（此处为圆形）
      legend
        .append("circle")
        .attr("cx", 10)
        .attr("cy", 10)
        .attr("r", 7)
        .attr("stroke",'#fff')
        .attr("stroke-width",1.5)
        .style("fill", _this.colorNodeList[0]);

      legend
        .append("circle")
        .attr("cx", 10)
        .attr("cy", 28)
        .attr("r", 7)
        .attr("stroke",'#fff')
        .attr("stroke-width",1.5)
        .style("fill", _this.colorNodeList[1]);

      legend
        .append("line")
        .attr("x1", 3)
        .attr("y1", 45)
        .attr("x2", 17)
        .attr("y2", 45)
        .attr("stroke-width",2)
        .style("stroke", _this.colorLinkList[0]);

      legend
        .append("line")
        .attr("x1", 3)
        .attr("y1", 63)
        .attr("x2", 17)
        .attr("y2", 63)
        .attr("stroke-width",2)
        .style("stroke", _this.colorLinkList[1]);

      legend.append("text")
        .attr("x",25)
        .attr("y",15)
        .style("fill","#0B2539")
        .text("Paper")

      legend.append("text")
        .attr("x",25)
        .attr("y",33)
        .style("fill","#0B2539")
        .text("Author")

      legend.append("text")
        .attr("x",25)
        .attr("y",51)
        .style("fill","#0B2539")
        .text("Cite")

      legend.append("text")
        .attr("x",25)
        .attr("y",69)
        .style("fill","#0B2539")
        .text("Write")



      // 在图例中添加文本标签
      // legend.selectAll("mylabels")
      //   .data(color.domain())
      //   .enter()
      //   .append("text")
      //   .attr("x", 30)
      //   .attr("y", function(d,i){ return 10+i*25})
      //   .style("fill", function(d){ return color(d)})
      //   .style("opacity",0.8)
      //   .text(function(d){ return  d})
      //   .attr("text-anchor", "left")
      //   .style("alignment-baseline", "middle")


      var g = svg.append('g') //包裹所有的node和link


      g.append("g")
        .selectAll("marker")
        .data(types)
        .join("marker")
        .attr("id", d => `arrow-${d}`)
        .attr('fill',_this.colorArrow)//箭头颜色
        .attr("markerUnits","userSpaceOnUse")
        .attr("viewBox", "0 -5 10 10")//坐标系的区域
        .attr("refX",30)//箭头坐标
        .attr("refY", 0)
        .attr("markerWidth", 15)//标识的大小
        .attr("markerHeight", 15)
        .attr("orient", "auto-start-reverse")//绘制方向，可设定为：auto（自动确认方向）和 角度值
        .attr("stroke-width",0)//箭头宽度
        .append("path")
        .attr("d", "M0,-5L10,0L0,5");//箭头的路径

      var link = g.append("g")
        .selectAll("line")
        .data(links)
        .join("line")
        .attr("stroke", _this.colorLink)
        .attr("stroke-opacity", 1)
        .attr("stroke-width", d =>d.strength*3)
        .attr("marker-end", `url(#resolved)`)
        .attr("marker-end", d =>`url(#arrow-${d.group})`);
      // `url(${new URL(`#arrow-${d.type}`)})`);



      var node = g.append("g")
        .attr("stroke", '#fff')
        .attr("stroke-width", 2)
        .selectAll("circle")
        .data(nodes)
        .join("circle")
        .attr("r", 15)
        .attr("fill", _this.colorNode)
        .call(_this.drag(simulation))
        .on("dblclick",_this.clicknode)
        .on("click", _this.queryinformation)
        .on('mouseover', function (d) {
          d3.select(this).transition().attr("r", 25).attr("stroke-width", 5)
        })
        .on('mouseout', function (d) {
          d3.select(this).transition().attr("r", 15).attr("stroke-width", 2)
        });

      d3.select("svg").on("dblclick.zoom", null);

      // node.append("title")
      //   .text(d => d.id);

      //显示节点文本在节点上
      var nodeText = g.append("g")
        .selectAll("text")
        .data(nodes)
        .join("text")
        .attr("dx", 0)
        .attr("dy", 0)
        .attr("class", "node_text")
        .text(function (d) {
          if (d.group===1){
            // if(!d.prop[0]["title"])console.log(d);
            return d.prop[0]["title"].substring(0,12)+"..."
          }else if (d.group===2){
            return d.prop[0]["authorName"]
          }
        })
        .attr("dx", function () {
          return this.getBoundingClientRect().width / 2 * (-1)
        })
        .attr("dy", 20)


      simulation.on("tick", () => {
        link
          .attr("x1", d => d.source.x)
          .attr("y1", d => d.source.y)
          .attr("x2", d => d.target.x)
          .attr("y2", d => d.target.y);

        node
          .attr("cx", d => d.x)
          .attr("cy", d => d.y);

        nodeText
          .attr("x", d => d.x)
          .attr("y", d => d.y);

      });

    },

    colorNode(d) {
      return this.colorNodeList[d.group-1];
    },

    colorLink(d){
      return this.colorLinkList[d.group-1];
    },

    colorArrow(d){
      return this.colorLinkList[d-1];
    },

    drag(simulation) {
      function dragstarted(d) {
        if (!d3.event.active) simulation.alphaTarget(0.3).restart();
        d.fx = d.x;
        d.fy = d.y;
      }

      function dragged(d) {
        d.fx = d3.event.x;
        d.fy = d3.event.y;
      }

      function dragended(d) {
        if (!d3.event.active) simulation.alphaTarget(0);
        d.fx = null;
        d.fy = null;
      }

      return d3.drag()
        .on("start", dragstarted)
        .on("drag", dragged)
        .on("end", dragended);
    },

    queryinformation(d) {

      //updata click_id
      if (d.group===1){
        this.searchnode=d
      }

      d3.select(".information").selectAll("svg").remove()

      var table=d3.select(".information")
        .append("svg")
        .attr('width', 1200)
        .attr('height', 2000)
      var tip = table.append("g");

      this.updated_dy="0em";
      var sum_dy=0; //记录前面文字的长度

      tip.append("rect")
        .attr("width",1200)
        .attr("height",2000)
        .style('fill', 'white');

      // .attr("padding",20)
      const key = Object.keys(d.prop[0])//读取属性key

      // var update_dy="0em";

      for (var i = 0; i < (key.length); i++) {

        const x = 0;
        var col = key[i];
        var dy=parseInt(this.updated_dy);
        sum_dy=sum_dy+dy;
        tip.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 50 + i * 30+sum_dy*16.5) //30是每个key的间距
          .text(col + ": ")

        var value = String(d.prop[0][col]);
        tip.append("text")
          .text(value)
          .call(this.wrap, 150, 50 + i * 30 + sum_dy * 16.5, 1100);
      }

    },

    queryrecommended(d){
      //展示推荐的文字结果
      d3.select(".results").selectAll("svg").remove()

      const resultplace = d3.select(".results")
        .append("svg")
        .attr("height", 5000)
        .attr("width", 400)  //调整KG的大小和位置
        .attr("ViewBox", [0, 0, 320, 650])

      var g = resultplace.append('g')

      this.updated_dy="0em";
      var sum_dy=0; //记录前面文字的长度

      g.append("rect")
        .attr("width",400)
        .attr("height",5000)
        .style('fill', 'white');

      console.log(d)

      for (var i = 0; i < 5; i++) {
        const x = 0;
        var dy=parseInt(this.updated_dy);

        sum_dy=sum_dy+dy;

        var n=i+1

        g.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 30 + i * 250+sum_dy*16.5) //30是每个key的间距
          .text("["+n+"]")

        //title
        g.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 60 + i * 250+sum_dy*16.5) //30是每个key的间距
          .text("title: ")

        var value = String(d[i]["prop"][0]["title"]);
        g.append("text")
          .text(value)
          .call(this.wrap, 150, 50 + i * 250 + sum_dy * 16.5, 250);

        dy=parseInt(this.updated_dy);

        sum_dy=sum_dy+dy;



        //Author
        g.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 60 + i * 250+30+sum_dy*16.5) //30是每个key的间距
          .text("author: ")

        var value = String(d[i]["prop"][0]["author"]);
        g.append("text")
          .text(value)
          .call(this.wrap, 150, 50 + i * 250+30 + sum_dy * 16.5, 250);

        dy=parseInt(this.updated_dy);

        sum_dy=sum_dy+dy;

        //publisher
        g.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 60 + i * 250+60+sum_dy*16.5) //30是每个key的间距
          .text("publisher: ")

        var value = String(d[i]["prop"][0]["publisher"]);
        g.append("text")
          .text(value)
          .call(this.wrap, 150, 50 + i * 250 +60+ sum_dy * 16.5, 250);

        dy=parseInt(this.updated_dy);

        sum_dy=sum_dy+dy;

        //year
        g.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 60 + i * 250+90+sum_dy*16.5) //30是每个key的间距
          .text("year: ")

        var value = String(d[i]["prop"][0]["year"]);
        g.append("text")
          .text(value)
          .call(this.wrap, 150, 50 + i * 250+90 + sum_dy * 16.5, 250);

        dy=parseInt(this.updated_dy);

        sum_dy=sum_dy+dy;

        //vicuna_summary
        g.append("text")
          .attr("dx", 0) //变量名
          .attr("y", 60 + i * 250+120+sum_dy*16.5) //30是每个key的间距
          .text("vicuna_summary: ")

        var value = String(d[i]["prop"][0]["vicuna_summary"]);
        g.append("text")
          .text(value)
          .call(this.wrap, 150, 50 + i * 250+120 + sum_dy * 16.5, 250);

      }
    },

    wrap(text, dx,y, width) {
      var _this=this
      text.each(function () {
        var text = d3.select(this),
          words = text.text().split(/\s+/).reverse(),
          word,
          line = [],
          lineNumber = 0,
          lineHeight = 1.1, // ems
          dy = 0, //parseFloat(text.attr("dy")),
          tspan = text.text(null)
            .append("tspan")
            .attr("x", dx)
            .attr("y", y)
            .attr("dy", dy + "em");
        while (word = words.pop()) {
          line.push(word);
          tspan.text(line.join(" "));
          if (tspan.node().getComputedTextLength() > width) {
            line.pop();
            tspan.text(line.join(" "));
            line = [word];
            tspan = text.append("tspan")
              .attr("x", dx)
              .attr("y", y)
              .attr("dy", ++lineNumber * lineHeight + dy + "em")
              .text(word);
          }
        }
        var updated_dy=+lineNumber * lineHeight + dy + "em";
        _this.returnupdated(updated_dy);
      })
    },

    returnupdated(updated_dy){
      this.updated_dy=updated_dy;
    },

    changeselected(event) {
      this.selected=event.target.value
    },

    recommendation(){
      var _this=this

      //初始化
      _this.recommendpaper=[]

      let parameter=new FormData();
      parameter.append("title",_this.searchnode.prop[0]["title"]);

      //引用python推荐算法
      _this.axios.post("/pyapi/recommend",parameter)
        .then(
          async function(response){
            const newtitle = response.data

            //遍历搜索找到得的title（储存在newtitle里）并返回node的值
            for(var i=0;i<(newtitle.length);i++){
              await _this.waitforme(4000);
              // let stren=newtitle[i][2]
              // let flag=true //flag用于记录是否是非重复值
               _this.axios.get("/api/paper/papertitle/" + newtitle[i][0]) //查询对应节点的具体信息
                .then(function (response2) {
                  console.log(response2.data)
                  _this.recommendpaper.push(response2.data)
                  //遍历找寻重复的node，response2应该只返回一个结果
                  // for (var j=0;j<_this.testGraph.nodes.length;j++){
                  //   if (_this.testGraph.nodes[j].id===response2.data["id"]){
                  //     flag=false
                  //     break
                  //   }
                  // }
                  // if (flag){
                  //   _this.testGraph["nodes"].push(response2.data)
                  //   _this.testGraph["links"].push({
                  //     "source":_this.searchnode["id"],
                  //     "target":response2.data["id"],
                  //     "group":3,
                  //     "strength":Math.log(stren)
                  //   })
                  // }//不重复就加入node和link
                })
            }
            _this.queryrecommended(_this.recommendpaper);
            // _this.testGraph["nodes"].push({"id":_this.searchnode["id"],"prop":_this.searchnode["prop"],"group":1})
            // console.log(_this.testGraph)
            // _this.initGraph(_this.testGraph)
          }).catch(function(error) {
            console.log(error)
          })


    },

    waitforme(millisec) {
    return new Promise(resolve => {setTimeout(() => { resolve('') }, millisec);})
  }
  }
}



</script>
<!-- Add "scoped" attribute to limit CSS to this component only -->

<style>
  h1 {
    font-weight: 1000;
    font-size:30px;
    width:1350px;
  }
  .searchmain{
    display:grid;
    grid-template-columns: 1fr 1fr;
    //grid-gap: 0px;
    position:relative;
    width:800px;
    //margin-bottom: 20px;
    padding:20px;
  }
  .searchmain p{
    font-size: 20px;
    font-weight: 800;
  }
  .searchinput{
    width:300px;
    height:20px;
  }
  .searchtype{
    margin-top:25px;
    margin-left:20px;
    width:200px;
    height:25px;
  }
  .main{
    display: grid;
    width:1350px;
    background: #BCCAD7;
    padding:10px;
    border-radius:8px;
    grid-template-columns: 1fr 1fr;
    grid-gap: 20px;
  }
  .container{
    position:relative;
    width: 800px;
    height: 650px;
    //border: 1px solid #2c3e50;
    //border-radius:8px;
    //background: #A6B9C9;
    padding:10px;
    //background:#FFFFFF;
  }
  .recommend{
    position:relative;
    width: 478px;
    height: 650px;
    border: 1px solid #2c3e50;
    border-radius:8px;
    background: #FFFFFF;
    padding:10px;
  }
  .recommendHeader{
    display: grid;
    width:357px;
    padding:10px;
    border-radius:8px;
    grid-template-columns: 1fr 1fr;
    grid-gap: 20px;
  }
  .recommendButton{
    width:250px;
    font-size: 15px;
    font-weight: 600;
    background-color: #CCD6DF;
    border:1.5px solid #2c3e50;
  }
  .recommendButton:hover{
    font-size: 15px;
    font-weight: 600;
    color: #2c3e50;
    background-color: #BCCAD7;
    border:2px solid #BCCAD7;
  }
  .recommendButton:focus{
    font-size: 15px;
    font-weight: 600;
    color:#277AC6;
    background-color: #BCCAD7;
    border:2px solid #2c3e50;
  }
  .results{
    position:relative;
    margin-top:20px;
    margin-left:10px;
    margin-right:10px;
    height:550px;
    border: 1px solid #2c3e50;
    border-radius:8px;
    overflow-y:auto;
    overflow-x:auto;
  }
  .information{
    position:relative;
    width:1350px;
    height:500px;
    margin-top:20px;
    border: 1px solid #2c3e50;
    border-radius:8px;
    padding:10px;
    overflow-y:auto;
  }
</style>
