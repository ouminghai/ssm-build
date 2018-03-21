<style lang="less">
    @import '../../styles/common.less';
    @import '../tables/components/table.less';
</style>

<template>
    <div>
        <Row :gutter="24">
            <Col span="24">
                <Card >
                    <p slot="title">
                        <Icon type="pinpoint"></Icon>
                        用户管理
                    </p>
                    <Row>
                        <Input v-model="searchConName1" icon="search" @on-change="handleSearch1" placeholder="请输入姓名搜索..." style="width: 200px" />
                    </Row>
                    <Row class="margin-top-10 searchable-table-con1">
                        <Table :columns="columns1" :data="data1"></Table>
                    </Row>
                </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import * as table from '../tables/data/search';
    import Vue from 'vue';
    export default {
        name: 'user-manager',
        data () {
            return {
                searchConName1: '',
                columns1: [
                    {
                        key: 'id',
                        title: '账户id'
                    },
                    {
                        key: 'userName',
                        title: '账户'
                    },
                    {
                        key: 'roleName',
                        title: '角色名称'
                    }
                ],
                data1: [],
                initTable1: [],
            };
        },
        methods: {
            init () {

                Vue.axios.post("users/alluser").then((response) =>{
                    if(response.data.resultCode == 200){
                        this.data1 = this.initTable1 = response.data.data;
                    }
                });

            },
            search (data, argumentObj) {
                let res = data;
                let dataClone = data;
                for (let argu in argumentObj) {
                    if (argumentObj[argu].length > 0) {
                        res = dataClone.filter(d => {
                            return d[argu].indexOf(argumentObj[argu]) > -1;
                        });
                        dataClone = res;
                    }
                }
                return res;
            },
            handleSearch1 () {
                this.data1 = this.initTable1;
                this.data1 = this.search(this.data1, {userName: this.searchConName1});
            }
        },
        mounted () {
            this.init();
        }
    };
</script>
