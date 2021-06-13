import request from '@/utils/request'

// 查询角色列表
export function listNews(query) {
  return request({
    url: '/business/news/list',
    method: 'get',
    params: query
  })
}

// 查询角色详细
export function getNews(roleId) {
  return request({
    url: '/business/news/' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addNews(data) {
  return request({
    url: '/business/news',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateNews(data) {
  return request({
    url: '/business/news',
    method: 'put',
    data: data
  })
}


// 删除角色
export function delNews(roleId) {
  return request({
    url: '/business/news/' + roleId,
    method: 'delete'
  })
}

// 导出角色
export function exportNews(query) {
  return request({
    url: '/business/news/export',
    method: 'get',
    params: query
  })
}
