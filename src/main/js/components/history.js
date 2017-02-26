import React from 'react';

import Record from './record'

class History extends React.Component {
  render() {
    return (
    <div id="history">
      <h2>History</h2>
      <div class="list-group">
        <Record key="1" method="GET" url="http://jsonplaceholder.typicode.com/posts/1" body=""/>
        <Record key="2" method="POST" url="http://jsonplaceholder.typicode.com/posts" body='{"userId": 1, "id": 101, "title": "foo", "body": "bar"}'/>
        <Record key="3" method="PUT" url="http://jsonplaceholder.typicode.com/posts/1" body='{"userId": 1, "id": 101, "title": "foo", "body": "bar"}'/>
        <Record key="4" method="DELETE" url="http://jsonplaceholder.typicode.com/posts/1" body=""/>
      </div>
    </div>
    );
  };
}

export default History;
