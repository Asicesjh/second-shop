import http from "../../http";
import { type CinemaList, type CinemaListParm} from "./CinemaListModel";
//列表
export const getCinemaListApi = (parm: CinemaListParm) => {
    return http.get("/api/cinema/list", parm)
}
//增加
export const addCinemaApi = (parm: object | undefined) => {
    return http.post("/api/cinema/release", parm)
}
//删除
export const deleteApi = (parm:CinemaList)=>{
    // console.log(parm)
    return http.delete(`/api/cinema/${parm.cinemaId}`)
} 
//编辑
export const editApi = (parm:CinemaList)=>{
    return http.put("/api/cinema",parm)
} 
//上架下架
export const upandownApi = (parm:CinemaList)=>{
    // console.log(parm)
    return http.post("/api/cinema/upandown",parm)
} 