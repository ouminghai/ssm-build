<style lang="less">
    @import './login.less';
</style>

<template>
    <div class="login" @keydown.enter="handleSubmit">



        <div class="login-con">

            <Alert>
                <div class="authot-info"> 台山市国家税务局信息中心：区明海    </div>
            </Alert>

            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎登录
                </p>
                <div class="form-con">
                    <Form ref="loginForm" :model="form" :rules="rules">
                        <FormItem prop="userName">
                            <Input v-model="form.userName" placeholder="请输入用户名">
                                <span slot="prepend">
                                    <Icon :size="16" type="person"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem prop="password">
                            <Input type="password" v-model="form.password" placeholder="请输入密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>登录</Button>
                        </FormItem>
                    </Form>
                    <p class="login-tip">输入任意用户名和密码即可</p>


                    <Button @click="handleRegister()" type="ghost" long>注册</Button>


                </div>
            </Card>
        </div>
    </div>
</template>

<script>
import Cookies from 'js-cookie';
import Vue from 'vue';
export default {
    data () {
        return {
            form: {
                userName: '',
                password: ''
            },
            rules: {
                userName: [
                    { required: true, message: '账号不能为空', trigger: 'blur' }
                ],
                password: [
                    { required: true, message: '密码不能为空', trigger: 'blur' }
                ]
            }
        };
    },
    methods: {
        handleSubmit () {
            this.$refs.loginForm.validate((valid) => {
                if (valid) {

                    Vue.axios.post("login/userlogin",{
                        userName :  this.form.userName,
                        password : this.form.password
                    }).then((response) =>{
                        console.log(response);

                        if(response.data.resultCode == 200){
                            Cookies.set('user', this.form.userName);
                            Cookies.set('password', this.form.password);
                            this.$store.commit('setAvator', '../images/avator.jpg');
                            if (this.form.userName === 'admin') {
                                Cookies.set('access', 0);
                            } else {
                                Cookies.set('access', 1);
                            }
                            this.$router.push({
                                name: 'home_index'
                            });

                        }else{

                            Vue.prototype.$Notice.open({
                                title: '登陆提示',
                                desc:  response.data.message
                            });

                        }

                    }).catch((e) =>{

                        Vue.prototype.$Notice.open({
                            title: '系统错误',
                            desc:  e.message
                        });

                    });





                }
            });
        },
        handleRegister(){
            this.$router.push({
                name: 'register'
            })
        }

    }
};
</script>

<style>

</style>
