import http from "../../http";
import { type ListParm, type SysUser, type UpdateParm } from "./SysUserModel";
//新增
export const addUserApi = (parm:SysUser)=>{
return http.post("/api/sysUser",parm)
}
//列表
export const getListApi = (parm:ListParm)=>{
    return http.get("/api/sysUser/getList",parm)
}
//编辑
export const editApi = (parm:SysUser)=>{
    return http.put("/api/sysUser",parm)
}
//删除
export const deleteApi = (userId:string)=>{
    return http.delete(`/api/sysUser/${userId}`)
}
//修改密码
export const updatePasswordApi = (parm:UpdateParm)=>{
    return http.put("/api/sysUser/updatePassword",parm)
}