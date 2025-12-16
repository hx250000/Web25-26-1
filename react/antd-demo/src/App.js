import { Layout, Menu } from 'antd';
//import { Color } from 'antd/es/color-picker';
import { UploadOutlined, UserOutlined, VideoCameraOutlined } from '@ant-design/icons';
//import React from 'react';
import UserSearch from './components/UserSearch';
import UserTable from './components/UserTable';
import QuestionSearch from './components/QuestionSearch';
import QuestionTable from './components/QuestionTable';
import { useState } from 'react';

const { Header, Footer, Sider, Content } = Layout;
const App = () => {
  const [selectedKey, setSelectedKey] = useState('1');
  return (
  <>
    <Layout>
      <Header><h1 style={{ color: '#ffffef' }}>Quiz管理系统</h1></Header>
      <Layout>
        <Sider trigger={null} >
          <Menu
            theme="dark"
            mode="inline"
            defaultSelectedKeys={['1']}
            onClick={({ key }) => setSelectedKey(key)}
            items={[
              { key: '1', icon: <UserOutlined />, label: '用户管理' },
              { key: '2', icon: <VideoCameraOutlined />, label: '题目管理' }
            ]}
          />
        </Sider>
        <Content>
          {selectedKey === '1' && (
            <>
              <UserSearch />
              <UserTable />
            </>
          )}
          {selectedKey === '2' && (
            <>
              <QuestionSearch />
              <QuestionTable />
            </>
          )}
        </Content>
      </Layout>
      <Footer style={{
          textAlign: 'center',
        }}>
        Quiz管理系统 ©2025 Created by xinghe
      </Footer>
    </Layout>
  </>
);
}
export default App;