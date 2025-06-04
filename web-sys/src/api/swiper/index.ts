import http from "../../http";
import type {Swiper,SwiperParm} from './SwiperModel'
export const getGoodsListApi = ()=>{
    return http.get("/api/swiper/getGoods")
}
// 新增
export const addSwiperApi = (parm:Swiper)=>{
    return http.post("/api/swiper",parm)
} 
//列表
export const getListApi = (parm:SwiperParm)=>{
    return http.get("/api/swiper/list",parm)
}
//上架下架
export const upandownApi = (parm:Swiper)=>{
    console.log(parm)
    return http.post("/api/swiper/upandown",parm)
} 
//编辑
export const editApi = (parm:Swiper)=>{
    return http.put("/api/swiper",parm)
} 
//删除
export const deleteApi = (parm:Swiper)=>{
    // console.log(parm)
    return http.delete(`/api/swiper/${parm.swiperId}`)
} 