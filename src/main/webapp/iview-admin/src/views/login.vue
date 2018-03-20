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
                            this.$store.commit('setAvator', 'data:image/png;base64,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');
                            if (this.form.userName === 'admin') {
                                Cookies.set('access', 0);
                            } else {
                                Cookies.set('access', 1);
                            }
                            this.$router.push({
                                name: 'user-mangaer'
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
