import React from 'react';

import Response from './response';

class Main extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      body: "I'm body"
    };

    this.handleSend = this.handleSend.bind(this);
  }

  handleSend() {
    $.get('http://localhost:8080/test')
      .done(data => {
        this.setState({body: data});
      });
  }

  render() {
    return (
      <div>
        <div class="container">
          <div class="row">
            <form>
              <div class="form-group">
                <label for="path">Path</label>
                <input type="text" class="form-control" id="path" placeholder="Path"/>
              </div>
              <button type="submit" class="btn btn-default" onClick={this.handleSend}>Send</button>
            </form>
          </div>
          <div class="row">
              <Response body={this.state.body}/>
          </div>
        </div>
      </div>
    );
  }
}

export default Main;