//定义用户的数据类型:interface 和 type
export type SysUser = {
    userId?:string,
    username:string,
    password:string,
    nickName:string,
    phone:string,
    sex:string,
    status:string
    }
//定义列表查询的参数
export type ListParm = {
    currentPage:number,
    pageSize:number,
    nickName:string,
    total?:number
}
//修改密码
export type UpdateParm = {
    password:string,
    userId:string
    oldPassword:string
}