export type Swiper = {
    swiperId: string,
    image: string,
    title:string,
    status: string,
    orderNum: string
}
export type SwiperParm = {
    currentPage: number,
    pageSize: number,
    title: string,
    total?: number
}