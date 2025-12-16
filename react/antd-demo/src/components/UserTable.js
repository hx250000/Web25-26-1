import { Space, Table } from 'antd';
//import React from 'react';
import { useState } from 'react';
const columns = [
    {
        title: '序号',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: '用户名',
        dataIndex: 'name',
        key: 'name',
        render: (text) => <a>{text}</a>,
    },
    {
        title: '日期',
        dataIndex: 'date',
        key: 'date',
    },
    {
        title: '操作',
        key: 'action',
        render: (_, record) => (
            <Space size="middle">
                <a>编辑</a>
                <a>删除</a>
            </Space>
        ),
    },
];
const data = [
    {
        key: '1',
        id: '1',
        name: 'John Brown',
        date: '2025-9-11'
    },
    {
        key: '2',
        id: '2',
        name: 'Jim Green',
        date: '2025-9-11'
    },
    {
        key: '3',
        id: '3',
        name: 'Joe Black',
        date: '2025-9-11'
    },
    {
        key: '4',
        id: '4',
        name: 'Joe Black',
        date: '2025-9-11'
    },
    {
        key: '5',
        id: '5',
        name: 'Joe Black',
        date: '2025-9-11'
    },
    {
        key: '6',
        id: '6',
        name: 'Xing He',
        date: '2025-9-11'
    }
];

const App = () => {
    const [currentPage, setCurrentPage] = useState(1);
    
    const handleTableChange = (pagination) => {
        setCurrentPage(pagination.current);
        console.log('当前页码：', pagination.current);
    };
    return (
        <Table
        columns={columns}
        dataSource={data}
        pagination={{
            current: currentPage, // 当前页码
            pageSize: 5, // 每页条数
            total: data.length, // 数据总数
            showSizeChanger: false,
            position: ['bottomLeft'],
            showQuickJumper: false,
            showTotal: false,
            onChange: () => { /* 页码或每页条数变化时的回调 */ },
        }}
        onChange={handleTableChange} />);
}
export default App;