<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form>
        <el-form-item>
          <el-button type="primary" icon="plus" @click="showCreate" v-if="hasPerm('order:add')">添加
          </el-button>
        </el-form-item>
      </el-form>
    </div>
    <el-table :data="list" v-loading.body="listLoading" element-loading-text="拼命加载中" border fit
              highlight-current-row>
      <el-table-column align="center" label="序号" width="80">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"> </span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="title" label="主题" ></el-table-column>
      <el-table-column align="center" prop="type" label="类型" ></el-table-column>
      <el-table-column align="center" prop="content" label="内容" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="axureurl" label="AxureURL" style="width: 60px;"></el-table-column>
      <el-table-column align="center" prop="html5url" label="html5URL" style="width: 60px;"></el-table-column>
      <el-table-column align="center" label="创建时间" width="170">
        <template slot-scope="scope">
          <span>{{scope.row.createTime}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" prop="duetime" label="预期截至时间" ></el-table-column>
      <el-table-column align="center" prop="status" label="状态" ></el-table-column>
      <el-table-column align="center" label="管理" width="200" v-if="hasPerm('order:update')">
        <template slot-scope="scope">
          <el-button type="primary" icon="edit" @click="showUpdate(scope.$index)">修改</el-button>
          <el-button type="danger" icon="delete" v-if="scope.row.id!=id "
                     @click="removeOrder(scope.$index)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.pageNum"
      :page-size="listQuery.pageRow"
      :total="totalCount"
      :page-sizes="[10, 20, 50, 100]"
      layout="total, sizes, prev, pager, next, jumper">
    </el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form class="small-space" :model="tempOrder" label-position="left" label-width="110px"
               style='width: 400px; margin-left:30px;'>
        <el-form-item label="标题">
          <el-input type="text" v-model="tempOrder.title">
          </el-input>
        </el-form-item>
        <el-form-item label="类型">
          <el-input type="text" v-model="tempOrder.type">
          </el-input>
        </el-form-item>
        <el-form-item label="详细描述">
          <el-input type="text" v-model="tempOrder.content">
          </el-input>
        </el-form-item>
        <el-form-item label="状态">
          <el-input type="text" v-model="tempOrder.status">
          </el-input>
        </el-form-item>
        <el-form-item label="AxureDemoURL">
          <el-input type="text" v-model="tempOrder.axureurl">
          </el-input>
        </el-form-item>
        <el-form-item label="Html5DemoURL">
          <el-input type="text" v-model="tempOrder.html5url">
          </el-input>
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button v-if="dialogStatus=='create'" type="success" @click="createOrder">创 建</el-button>
        <el-button type="primary" v-else @click="updateOrder">修 改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        totalCount: 0, //分页组件--数据总条数
        list: [],//表格的数据
        listLoading: false,//数据加载等待动画
        listQuery: {
          pageNum: 1,//页码
          pageRow: 50,//每页条数
          name: ''
        },
        dialogStatus: 'create',
        dialogFormVisible: false,
        textMap: {
          update: '编辑',
          create: '创建文章'
        },
        tempOrder: {
          id: "",
          title:"",
          type:"",
          content: "",
          status:"",
          axureurl:"",
          html5url:"",
        }
      }
    },
    created() {
      this.getList();
    },
    methods: {
      getList() {
        //查询列表
        if (!this.hasPerm('order:list')) {
          return
        }
        this.listLoading = true;
        this.api({
          url: "/order/listOrder",
          method: "get",
          params: this.listQuery
        }).then(data => {
          this.listLoading = false;
          this.list = data.list;
          this.totalCount = data.totalCount;
        })
      },
      handleSizeChange(val) {
        //改变每页数量
        this.listQuery.pageRow = val
        this.handleFilter();
      },
      handleCurrentChange(val) {
        //改变页码
        this.listQuery.pageNum = val
        this.getList();
      },
      getIndex($index) {
        //表格序号
        return (this.listQuery.pageNum - 1) * this.listQuery.pageRow + $index + 1
      },
      showCreate() {
        //显示新增对话框
        this.tempOrder.title = "";
        this.tempOrder.content = "";
        this.dialogStatus = "create"
        this.dialogFormVisible = true
      },
      showUpdate($index) {
        //显示修改对话框
        this.tempOrder.id = this.list[$index].id;
        this.tempOrder.title = this.list[$index].title;
        this.tempOrder.type = this.list[$index].type;
        this.tempOrder.content = this.list[$index].content;
        this.tempOrder.status = this.list[$index].status;
        this.tempOrder.axureurl = this.list[$index].axureurl;
        this.tempOrder.html5url = this.list[$index].html5url;
        this.dialogStatus = "update"
        this.dialogFormVisible = true
      },
      createOrder() {
        //保存新文章
        this.api({
          url: "/order/addOrder",
          method: "post",
          data: this.tempOrder
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      updateOrder() {
        //修改文章
        this.api({
          url: "/order/updateOrder",
          method: "post",
          data: this.tempOrder
        }).then(() => {
          this.getList();
          this.dialogFormVisible = false
        })
      },
      removeOrder($index) {
        let _vue = this;
        this.$confirm('确定删除此订单?', '提示', {
          confirmButtonText: '确定',
          showCancelButton: false,
          type: 'warning'
        }).then(() => {
          let user = _vue.list[$index];
          user.deleteStatus = '2';
          _vue.api({
            url: "/order/removeOrder",
            method: "post",
            data: user
          }).then(() => {
            _vue.getList()
          }).catch(() => {
            _vue.$message.error("删除失败")
          })
        })
      },
    }
  }
</script>

