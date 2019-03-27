require 'spec_helper'

RSpec.describe 'Parking Lot' do
  let(:pty) { PTY.spawn('parking_lot') }

  before(:each) do |test|
    run_command(pty, "create_parking_lot 3\n") unless test.metadata[:no_before]
  end

  it "can create a parking lot", :sample => true do
    expect(fetch_stdout(pty)).to end_with("Created a parking lot with 3 slots\n")
  end

  it "can park a car" do
    run_command(pty, "park KA-01-HH-3141 Black\n")
    expect(fetch_stdout(pty)).to end_with("Allocated slot number: 1\n")
  end
  
  it "can unpark a car" do
    run_command(pty, "park KA-01-HH-3141 Black\n")
    run_command(pty, "leave 1\n")
    expect(fetch_stdout(pty)).to end_with("Slot number 1 is free\n")
  end
  
  it "can report status" do
    run_command(pty, "park KA-01-HH-1234 White\n")
    run_command(pty, "park KA-01-HH-3141 Black\n")
    run_command(pty, "park KA-01-HH-9999 White\n")
    run_command(pty, "status\n")
    expect(fetch_stdout(pty)).to end_with(<<-EOTXT
Slot No.    Registration No    Colour
1           KA-01-HH-1234      White
2           KA-01-HH-3141      Black
3           KA-01-HH-9999      White
EOTXT
)
  end

  it "can show error if create a parking lot with invalid size" do
    run_command(pty, "create_parking_lot -1\n")
    expect(fetch_stdout(pty)).to end_with("Failed to create a parking lot because: Size must be at least 1, found size: -1\n")
  end

  it "can show error if command does not exist" do
    run_command(pty, "not_exist_command\n")
    expect(fetch_stdout(pty)).to end_with("Command not found: not_exist_command\n")
  end

  it "can show error if park a car when parking lot is full" do
    run_command(pty, "park KA-01-HH-1234 White\n")
    run_command(pty, "park KA-01-HH-3141 Black\n")
    run_command(pty, "park KA-01-HH-9999 White\n")
    run_command(pty, "park KA-01-HH-8934 Red\n")
    expect(fetch_stdout(pty)).to end_with("Sorry, parking lot is full\n")
  end

  it "can show error if unpark a car from empty slot" do
    run_command(pty, "leave 1\n")
    expect(fetch_stdout(pty)).to end_with("Sorry, slot number 1 is already free\n")
  end

  it "can show error if unpark a car from invalid slot" do
    run_command(pty, "leave -1\n")
    expect(fetch_stdout(pty)).to end_with("Slot number is out of range: -1\n")
  end

  it "can show error if modify parking lot before create", :no_before do
    run_command(pty, "park KA-01-HH-1234 White\n")
    expect(fetch_stdout(pty)).to end_with("create_parking_lot must be successfully executed first\n")
    run_command(pty, "status\n")
    expect(fetch_stdout(pty)).to end_with("create_parking_lot must be successfully executed first\n")
    run_command(pty, "leave 1\n")
    expect(fetch_stdout(pty)).to end_with("create_parking_lot must be successfully executed first\n")
  end
end
