import React from 'react';

import History from './history';
import Request from './request';
import Response from './response';

class Main extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      res: {
        status: '',
        body: ''
      }
    };
  }

  handleSend(req) {
    $.post('/http/send', JSON.stringify(req))
      .done(res => {
        this.setState({res: res});
      });
  }

  render() {
    return (
      <div class="container">
        <div class="page-header">
          <h1>Http Client <small>ver 0.0.1</small></h1>
        </div>
        <div class="row">
          <div class="col-md-4">
            <History />
          </div>
          <div class="col-md-8">
            <Request onSend={this.handleSend.bind(this)} />
            <Response status={this.state.res.status} body={this.state.res.body} />
          </div>
        </div>
      </div>
    );
  }
}

export default Main;