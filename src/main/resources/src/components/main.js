import React from 'react';

class Main extends React.Component {
  render() {
    return (
      <div>
        <div class="container">
          <form>
            <div class="form-group">
              <label for="path">Path</label>
              <input type="text" class="form-control" id="path" placeholder="Path"/>
            </div>
            <button type="submit" class="btn btn-default">Submit</button>
          </form>
        </div>
      </div>
    );
  }
}

export default Main;