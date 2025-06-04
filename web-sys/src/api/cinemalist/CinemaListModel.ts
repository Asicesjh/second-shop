//列表查询数据类型
export type CinemaListParm = {
    currentPage: number,
    pageSize: number,
    cinemaName: string,
    total?: number
}
export type CinemaList={
    cinemaId:string
    cinemaName: string
    image: string
    price: string
    category: string
    status:string
}