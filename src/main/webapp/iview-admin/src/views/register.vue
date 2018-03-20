<style lang="less">
    @import './register.less';
</style>

<template>
    <div class="register" @keydown.enter="handleSubmit">
        <div class="login-con">
            <Card :bordered="false">
                <p slot="title">
                    <Icon type="log-in"></Icon>
                    欢迎注册
                </p>
                <div class="form-con">
                    <Form ref="registerForm" :model="form" :rules="rules">
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
                        <FormItem prop="password">
                            <Input type="password" v-model="form.repassword" placeholder="请再次确认密码">
                                <span slot="prepend">
                                    <Icon :size="14" type="locked"></Icon>
                                </span>
                            </Input>
                        </FormItem>
                        <FormItem>
                            <Button @click="handleSubmit" type="primary" long>注册</Button>
                        </FormItem>
                    </Form>
                    <p class="login-tip">输入任意用户名和密码即可</p>

                    <Button @click="handleLogin()" type="ghost" long>登陆</Button>

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
                password: '',
                repassword:'',
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
            this.$refs.registerForm.validate((valid) => {
                if (valid) {
                    if(this.form.password != this.form.repassword) {
                        this.$Message.error('两次密码必须一致');return true;
                    }else{

                        Vue.axios.post("register/register",
                                {
                            userName :  this.form.userName,
                            password : this.form.password,
                                    repassword: this.form.repassword
                        }
                        ).then((response) =>{

                            if(response.data.resultCode == 200){
                                this.$router.push({
                                    name: 'login'
                                });
                            }else{
                                Vue.prototype.$Notice.open({
                                    title: '注册提示',
                                    desc:  response.data.message
                                });
                            }
                        });

                    }
                }
            });
        },
        handleLogin(){
            this.$router.push({
                name: 'login'
            })
        }
    }
};
</script>

<style>

</style>
