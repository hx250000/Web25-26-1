//import { render } from '@testing-library/react';
import { Space, Table } from 'antd';
//import React from 'react';
import { useState } from 'react';
//import { rowKey } from 'antd/es/table/utils/commonUtils';
const columns = [
    {
        title: '序号',
        dataIndex: 'id',
        key: 'id',
    },
    {
        title: '题目',
        dataIndex: 'question',
        key: 'question',
        render: (text) => <a>{text}</a>,
    },
    {
        title: '选项',
        dataIndex: 'options',
        key: 'options',
        render: (options) => options.join(', ')
    },
    {
        title: '答案',
        dataIndex: 'answer',
        key: 'answer',
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
        question: '什么是React？',
        options: ['A. 一个库', 'B. 一个框架', 'C. 一个语言'],
        answer: 'A'
    },
    {
        key: '2',
        id: '2',
        question: 'React的生命周期函数有哪些？',
        options: ['A. componentDidMount', ' B. componentDidUpdate', ' C. componentWillUnmount'],
        answer: 'A'
    },
    {
        key: '3',
        id: '3',
        question: 'React的状态管理有哪些方式？',
        options: ['A. Redux', ' B. MobX', ' C. Context API'],
        answer: 'A'
    },
    {
        key: '4',
        id: '4',
        question: 'React的性能优化手段有哪些？',
        options: ['A. 代码分割', ' B. 懒加载', 'C. 服务器端渲染'],
        answer: 'A'
    },
    {
        key: '5',
        id: '5',
        question: 'React的路由管理有哪些方式？',
        options: ['A. React Router', 'B. Next.js', 'C. Reach Router'],
        answer: 'A'
    },
    {
        key: '6',
        id: '6',
        question: '下列关于进程和线程的描述，正确的是',
        options: [' A. 无论系统是否支持线程，进程都是资源分配的基本单位',
            'B. 线程是资源分配的基本单位，进程是调度的基本单位',
            'C. 内核线程和用户线程的切换都需要内核支持。用户不需要',
            'D. 同一进程的各个线程拥有各自不同的地址空间'],
        answer: 'A'
    },
    {
        key: '7',
        id: '7',
        question: '在分时系统中，当一个用户进程执行完一个时间片后，该进程的状态转化为',
        options: [' A. 运行->等待', ' B. 就绪->运行', ' C. 运行->终止', ' D. 运行->就绪'],
        answer: 'D'
    }
    /*
    1、下列关于进程和线程的描述，正确的是(   A  )。
    A. 无论系统是否支持线程，进程都是资源分配的基本单位
    B. 线程是资源分配的基本单位，进程是调度的基本单位
    C. 内核线程和用户线程的切换都需要内核支持。用户不需要
    D. 同一进程的各个线程拥有各自不同的地址空间
    2、在分时系统中，当一个用户进程执行完一个时间片后，该进程的状态转化为（  D  ）。
    A. 运行->等待    B. 就绪->运行    C. 运行->终止    D. 运行->就绪
    */
];

const onChange = (pagination, filters, sorter, extra) => {
    console.log('params', pagination, filters, sorter, extra);
};

const App = () => {
    const [currentPage, setCurrentPage] = useState(1);
    
    const handleTableChange = (pagination) => {
        setCurrentPage(pagination.current);
        console.log('当前页码：', pagination.current);
    };

    return (<Table
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
        onChange={handleTableChange} 
        rowKey='key'
        />);
}
export default App;